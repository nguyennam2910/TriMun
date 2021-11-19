package com.sundogsoftware.spark

import org.apache.spark.sql.functions._
import org.apache.spark._
import org.apache.log4j._
import org.apache.spark.sql._
import org.apache.spark.sql.types.{IntegerType, LongType, StructType}

object ZedianSimilarityMovie {
  case class Movies(userID: Int, movieID: Int, rating: Int, timestamp: Long)
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val ss = SparkSession.builder.master("local[*]").appName("ZedianSimilarity").getOrCreate()

    val moviesSchema = new StructType()
      .add("userID", IntegerType, true)
      .add("movieID", IntegerType, true)
      .add("rating", IntegerType, true)
      .add("timestamp", LongType, true)

    import ss.implicits._
    val movies = ss.read
      .option("sep", "::")
      .schema(moviesSchema)
      .csv("data/ml-1m/ratings.dat")
      .as[Movies]

    val movieGroup = movies.as("a").join(movies.as("b"), $"a.movieID" < $"b.movieID" && $"a.userID" === $"b.userID")
      .show()

    ss.stop()
  }
}
