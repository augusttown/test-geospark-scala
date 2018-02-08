package local.scala.test

import scala.util.parsing.json._

object TestScalaJson {

  def main(args: Array[String]): Unit = {
    //println("Test scala json")

  }

  def loadJson(): Unit = {

    val jsonFromStr = JSON.parseFull("""
    {
      "Library": {
        "book": [{
          "title": "Scala for Java Developers",
          "quantity": 10,
          "price": 25.50
        }, {
          "title": "Programming Scala",
          "quantity": 20,
          |"price": 10.50
        }]
      }
    }
    """)

  }

}
