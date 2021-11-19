package com.sundogsoftware.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark._
import org.apache.log4j._
import org.apache.spark.sql.functions
import org.apache.spark.sql.types.{IntegerType, FloatType, StructType, LongType}

object ZedianMovie {
  def parseLine(line: String): (Int, Int) = {
    val field = line.split("\t")
    (field(1).toInt, 1)
  }

  case class Rating(userID: Int, movieID: Int, rate: Int, timestamp: Long)

  def main(args: Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)
    /* RDD
    val rdd = new SparkContext("local[*]", "ZedianMoviePopular")
    val data = rdd.textFile("data/ml-100k/u.data").map(parseLine)
    val count = data.reduceByKey((x, y) => x + y).sortByKey(false).collect()
    count.foreach(println)
     */

    //DS
    val ds = SparkSession.builder().appName("ZedianMoviePopular").master("local[*]")
      .config("spark.sql.warehouse.dir", "file:///C:/temp").getOrCreate()
    val schema = new StructType()
      .add("userID", IntegerType, nullable = true)
      .add("movieID", IntegerType, nullable = true)
      .add("rate", IntegerType, nullable = true)
      .add("timestamp", LongType, nullable = true)

    import ds.implicits._
    val data = ds.read.schema(schema)
      .option("sep", "\t")
      .csv("data/ml-100k/u.data")
      .as[Rating]
    val res = data.groupBy("movieID").count().sort($"count".desc).show()

    ds.stop()
  }

}
