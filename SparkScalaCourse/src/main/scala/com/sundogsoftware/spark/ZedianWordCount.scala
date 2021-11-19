package com.sundogsoftware.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j._

object ZedianWordCount {
  case class Book(value: String)
  def main(args: Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)

    val ss = SparkSession.builder().appName("ZedianWordCount").master("local[*]")
      .config("spark.sql.warehouse.dir", "file:///C:/temp")
      .getOrCreate()

    import ss.implicits._
    val data = ss.read.text("data/book.txt").as[Book]

    val transformSchema = data.flatMap(x => x.value.split("\\W+")).filter($"value" =!= "")

    val wordCountSorted = transformSchema.select(lower($"value").as("word")).groupBy("word")
      .agg(count("word").as("no")).sort($"no".desc)
    transformSchema.printSchema()
    //wordCountSorted.show(wordCountSorted.count().toInt)
    val test = transformSchema.filter((length($"value") > 2) && (length($"value") < 5)).withColumnRenamed("value", "New")
    test.printSchema()
    test.show()

    ss.stop()
  }
}
