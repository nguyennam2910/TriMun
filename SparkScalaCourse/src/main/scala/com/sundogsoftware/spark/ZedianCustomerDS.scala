package com.sundogsoftware.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j._
import org.apache.spark.sql.types.{FloatType, IntegerType, StringType, StructType}

object ZedianCustomerDS {
  case class Transaction(customerID: Int, orderID: Int, payAmount: Float)
  def main(args: Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)

    val ss = SparkSession.builder()
      .master("local[*]")
      .appName("ZedianCustomerDS")
      .getOrCreate()

    val schema = new StructType()
      .add("customerID", IntegerType, nullable = true)
      .add("orderID", IntegerType, nullable = true)
      .add("payAmount", FloatType, nullable = true)

    import ss.implicits._
    val data = ss.read.schema(schema).csv("data/customer-orders.csv").as[Transaction]

    val transform = data.groupBy("customerID").agg(round(sum("payAmount"),2).as("total"))

    //val sortTotalPay = transform.select("customerID", "total").sort("total").show()

    val mostBuyItem = data.groupBy("orderID").agg(count("orderID").as("Buy Time")).sort($"Buy Time".desc).show()

    ss.stop()
  }
}
