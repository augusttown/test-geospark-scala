package local.scala.test

import scala.xml._

object TestScalaXml {

  def main(args: Array[String]): Unit = {
    //println("Test scala xml")
    testLoadXml();
  }

  def testLoadXml(): Unit = {

    val booksFromXml =
      <Library>
        <book title="Programming in Scala" quantity="15" price="30.00" />
        <book title="Scala for Java Developers" quantity="10" price="25.50" />
      </Library>

    val booksFromXmlStr = XML.loadString("""
      <Library>
        <book title="Programming in Scala" quantity="15" price="30.00"/>
        <book title="Scala for Java Developers" quantity="10" price="25.50"/>
      </Library>
    """)

    val booksFromList =
      <Library>
        { List("Programming in Scala,15,30.00","Scala for Java Developers,10,25.50") map { row =>
          row split ","
        } map { b =>
          <book title={b(0)} quantity={b(1)} price={b(2)} />
        }}
      </Library>

    val total = (for {
      book <- booksFromXml \ "book"
      price = ( book \ "@price").text.toDouble
      quantity = ( book \ "@quantity").text.toInt
    } yield price * quantity).sum

  }

}
