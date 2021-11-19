package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.log4j._
import org.apache.spark.util.LongAccumulator
import scala.collection.mutable.ArrayBuffer

object ZedianBFS {
  type BFSData = (Array[Int], Int, String)
  type BFSNode = (Int, BFSData)

  val startCharacterID = 5306 //SpiderMan
  val targetCharacterID = 14 //ADAM 3,031 (who?)

  // We make our accumulator a "global" Option so we can reference it in a mapper later.
  var hitCounter:Option[LongAccumulator] = None

  def readFile(sc:SparkContext): RDD[BFSNode] = {
    val input = sc.textFile("data/marvel-graph.txt")
    input.map(convertToBFSNode)
  }

  def convertToBFSNode(s: String): BFSNode = {
    val fields = s.split("\\s+")
    val heroID = fields(0).toInt
    val connections: ArrayBuffer[Int] = ArrayBuffer()
    for (x <- 1 until fields.length) {
      connections += fields(x).toInt
    }
    var color = "WHITE"
    var distance = 9999
    if (heroID == startCharacterID) {
      distance = 0
      color = "GRAY"
    }
    (heroID, (connections.toArray, distance, color))
  }

  def mapBFS(s: BFSNode): Array[BFSNode] = {
    var res:ArrayBuffer[BFSNode] = ArrayBuffer()
    val curID = s._1
    val data = s._2
    var color = data._3
    val distance = data._2
    val connections = data._1

    if (color == "GRAY") {
      for (connection <- connections) {
        val newNode: BFSNode = (connection, (Array(), distance + 1, "GRAY"))
        res += newNode

        if (connection == targetCharacterID) {
          if (hitCounter.isDefined) {
            hitCounter.get.add(1)
          }
        }
      }
      color = "BLACK"
    }
    val curNode:BFSNode = (curID, (connections, distance, color))
    res += curNode
    res.toArray
  }

  def reduceBFS(data1: BFSData, data2: BFSData): BFSData = {
    val edges1:Array[Int] = data1._1
    val edges2:Array[Int] = data2._1
    val distance1:Int = data1._2
    val distance2:Int = data2._2
    val color1:String = data1._3
    val color2:String = data2._3

    // Default node values
    var distance:Int = 9999
    var color:String = "WHITE"
    var edges:ArrayBuffer[Int] = ArrayBuffer()

    // See if one is the original node with its connections.
    // If so preserve them.
    if (edges1.length > 0) {
      edges ++= edges1
    }
    if (edges2.length > 0) {
      edges ++= edges2
    }

    // Preserve minimum distance
    if (distance1 < distance) {
      distance = distance1
    }
    if (distance2 < distance) {
      distance = distance2
    }

    // Preserve darkest color
    if (color1 == "WHITE" && (color2 == "GRAY" || color2 == "BLACK")) {
      color = color2
    }
    if (color1 == "GRAY" && color2 == "BLACK") {
      color = color2
    }
    if (color2 == "WHITE" && (color1 == "GRAY" || color1 == "BLACK")) {
      color = color1
    }
    if (color2 == "GRAY" && color1 == "BLACK") {
      color = color1
    }
    if (color1 == "GRAY" && color2 == "GRAY") {
      color = color1
    }
    if (color1 == "BLACK" && color2 == "BLACK") {
      color = color1
    }

    (edges.toArray, distance, color)
  }

  def main(args:Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "ZedianBFS")
    var inputData = readFile(sc)
    hitCounter = Some(sc.longAccumulator("Hit Counter"))

    for (x <- 1 to 10) {
      inputData = inputData.flatMap(mapBFS)

      println("Running BFS Iteration# " + x)
      println("Processing " + inputData.count() + " values.")

      if (hitCounter.isDefined) {
        val hitCount = hitCounter.get.value
        if (hitCount > 0) {
          println("Hit the target character! From " + hitCount +
            " different direction(s).")
          return
        }
      }

      inputData = inputData.reduceByKey(reduceBFS)
    }
  }
}
