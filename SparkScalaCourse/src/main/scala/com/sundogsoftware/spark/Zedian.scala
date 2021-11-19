package com.sundogsoftware.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j._

object Zedian {
  case class Person(id: Int, name: String, age: Int, friends: Int)

  def main(args: Array[String]){
    Logger.getLogger("org").setLevel(Level.ERROR)
    val ss = SparkSession.builder.appName("Zedian").master("local[*]").getOrCreate()

    import ss.implicits._
    val people = ss.read.option("header", true).option("inferSchema", true).csv("data/fakefriends.csv").as[Person]
    people.printSchema()
    //val filter = people.select("age", "friends")
    val res = people.groupBy("age").agg(round(avg("friends"), 2).as("friends")).sort("age").show()

    ss.stop()
  }

}
