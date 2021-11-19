package com.sundogsoftware.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark._
import org.apache.log4j._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{StructType, IntegerType, StringType}

import scala.io.{Codec, Source}

object ZedianMostPopularHero {
  def parseLine(line: String): (Int, Int) = {
    val fields = line.split(" ")
    (fields(0).toInt, fields.length - 1)
  }

  def heroIdToName(): Map[Int, String] = {
    implicit val codec: Codec = Codec("ISO-8859-1")
    var map: Map[Int, String] = Map()
    val lines = Source.fromFile("data/Marvel-names.txt")
    for (line <- lines.getLines()) {
      val fields = line.split('\"')
      if (fields.length > 1) {
        map += (fields(0).trim().toInt -> fields(1))
      }
    }
    lines.close()
    map
  }

  case class HeroRela(value: String)
  case class HeroName(heroId: Int, name: String)

  def main(args: Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)

    //RDD
/*    val sc = new SparkContext("local[*]", "ZedianMostPopularHero")

    val rdd = sc.textFile("data/Marvel-graph.txt")
    val data = rdd.map(parseLine)

    val heroCount = data.reduceByKey((x, y) => x + y).map(x => (x._2, x._1))
    val nameHero = heroIdToName()
    val nameDict = sc.broadcast(nameHero)

    val res = heroCount.map(x => (x._1, nameDict.value.getOrElse(x._2, ""))).sortByKey(false).collect()

    res.foreach(println)*/

    //DS
    val ss = SparkSession.builder().master("local[*]").appName("ZedianMostPopularHero").getOrCreate()

    val schema = new StructType()
      .add("heroId", IntegerType, nullable = true)
      .add("Name", StringType, nullable = true)

    import ss.implicits._
    val heroName = ss.read.option("sep", " ").schema(schema).csv("data/Marvel-names.txt").as[HeroName]

    val countRelas: String => (Int, Int) = (s: String) => {
      val fields = s.split("\\s+")
      (fields(0).toInt, fields.length - 1)
    }
    val relaFunc = udf(countRelas)

    val heroRela = ss.read.text("data/Marvel-graph.txt").as[HeroRela]

    val transformCountRela = heroRela
      //.withColumn("value", trim($"value"))
      .withColumn("Id", split($"value", " ")(0))
      .withColumn("Connections", size(split($"value", " ")) - 1)

    val res = transformCountRela.groupBy("Id").agg(sum("Connections").as("Connections"))

    val nameWithCount = res.join(heroName, res("Id") === heroName("heroId"))
      .select("Name", "Connections")
      .sort($"Connections".desc)

    nameWithCount.show(false)

    val minCount = nameWithCount.sort($"Connections".asc).first()(1)
    val mostObscure = nameWithCount.filter($"Connections" === minCount).show(false)

    ss.stop()
  }
}
