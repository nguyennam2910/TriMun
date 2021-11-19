package com.sundogsoftware.spark

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{FloatType, IntegerType, StringType, StructType}

object ZedianTemperature {
  case class TemperatureManager(stationID: String, date: Int, tempType: String, tempF: Float)
  def main(args: Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)

    val schema = new StructType()
      .add("stationID", StringType, nullable = true)
      .add("date", IntegerType, nullable = true)
      .add("tempType", StringType, nullable = true)
      .add("tempF", FloatType, nullable = true)

    val ss = SparkSession
      .builder()
      .appName("ZedianTemperature")
      .master("local[*]")
      .getOrCreate()

    import ss.implicits._
    val managerSchema = ss.read.schema(schema)
      .csv("data/1800.csv")
      .as[TemperatureManager]
    val filterDS = managerSchema.filter($"tempType" === "TMIN")
    val groupStation = filterDS.groupBy("stationID").min("tempF")
    val transformF = groupStation.withColumn("min(tempF)", round($"min(tempF)" * 0.1f * (9.0f / 5.0f) + 32.0f, 2))
    transformF.show()

    ss.stop()
  }
}
