package com.sundogsoftware.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, LongType, StructType}
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import scala.io.Source
import scala.io.Codec

object ZedianPopularMovieBetter {
  def mapMovieIDtoName() : Map[Int, String] = {
    var map: Map[Int, String] = Map()
    implicit val codec: Codec = Codec("ISO-8859-1")
    val lines = Source.fromFile("data/ml-100k/u.item")
    for (line <- lines.getLines()) {
      val field = line.split('|')
      if (field.length > 1) {
        map += (field(0).toInt -> field(1))
      }
    }
    lines.close()
    map
  }
  case class MovieRating(movieId:Int, userId:Int, rating:Int, timestamp:Long)

  def main(args: Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)

    /*
    //RDD
    val map = mapMovieIDtoName()

    val sc = new SparkContext("local[*]", "ZedianPopularMovie")
    val rdd = sc.textFile("data/ml-100k/u.data").map(x => (x.split("\t")(1).toInt, 1))
    val movieCount = rdd.reduceByKey((x, y) => x + y)
    val movieCountName = movieCount.map(x => (map(x._1), x._2)).sortBy(x => x._2, false).collect()

    movieCountName.foreach(println)
    */


    //DS
    val ss = SparkSession.builder().appName("ZedianPopularMovieBetter").master("local[*]").getOrCreate()
    val schema = new StructType()
      .add("userId", IntegerType, nullable = true)
      .add("movieId", IntegerType, nullable = true)
      .add("rating", IntegerType, nullable = true)
      .add("timestamp", LongType, nullable = true)

    val map = mapMovieIDtoName()
    val dictMovie = ss.sparkContext.broadcast(map)

    val maping: Int => String = (movieId: Int) => {
      dictMovie.value(movieId)
    }

    import ss.implicits._
    val ds = ss.read.option("sep", "\t").schema(schema).csv("data/ml-100k/u.data").as[MovieRating]
    val movieCount = ds.groupBy("movieId").agg(count("movieId").as("Seen")).sort($"Seen".desc)

    val lookupFunc = udf(maping)
    val movieCountName = movieCount.withColumn("Name", lookupFunc(col("movieId"))).select("Name", "Seen").collect()
    movieCountName.foreach(println)

    ss.stop()
  }
}
