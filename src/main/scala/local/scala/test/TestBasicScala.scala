package local.scala.test

object TestBasicScala {

  case class Money(val amount:Int=1, val currency:String="USD")

  def main(args: Array[String]): Unit = {
    println("Test basic scala")
  }

  def testScalaSyntax() : Unit = {

      // one line function
    def max(x: Int, y: Int) = if (x > y) x else y
    def greet() = println("Hello, world!")

    // everything is method e.g. "to"
    for (i <- 0 to 2) print(i)
    for (i <- 0.to(2)) print(i)

    // try getting rid of vars in functional style
    def printArgs(args: Array[String]): Unit = {
      var i = 0
      while (i < args.length) {
        println(args(i))
        i += 1
      }
    }
    def printArgs1(args: Array[String]): Unit = {
      for (arg <- args)
        println(arg)
    }
    def printArgs2(args: Array[String]): Unit = {
      args.foreach(println)
    }
    //

  }

  def testScalaList(): Unit = {

    // List is immutable
    val oneTwo = List(1, 2)
    val threeFour = List(3, 4)
    // ::: append
    val oneTwoThreeFour = oneTwo ::: threeFour
    println(oneTwo + " and " + threeFour + " were not mutated.")
    println("Thus, " + oneTwoThreeFour + " is a new list.")

    // :: prepend
    val twoThree = List(2, 3)
    val oneTwoThree = 1 :: twoThree
    println(oneTwoThree)

    // reduceLeft
    val lines = List("1", "12", "123")
    val longestLine = lines.reduceLeft(
      (a, b) => if (a.length > b.length) a else b
    )

  }

  def testScalaTuple(): Unit = {

    val pair = (99, "Luftballons")
    println(pair._1)
    println(pair._2)


  }

  def testScalaSet(): Unit = {

    var jetSet = Set("Boeing", "Airbus")
    jetSet += "Lear"
    println(jetSet.contains("Cessna"))

    jetSet += "Cessna"


    import scala.collection.mutable

    val movieSet = mutable.Set("Hitch", "Poltergeist")
    movieSet += "Shrek"
    println(movieSet)
    movieSet.remove("Shrek")
    println(movieSet)

  }

  def testScalaMap(): Unit = {

    val romanNumeral = Map(
      1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V"
    )
    println(romanNumeral(4))

    import scala.collection.mutable
    val treasureMap = mutable.Map[Int, String]()
    treasureMap += (1 -> "Go to island.")
    treasureMap += (2 -> "Find big X on ground.")
    treasureMap += (3 -> "Dig.")
    println(treasureMap(2))

  }

  def testScalaClass(): Unit = {

    import scala.collection.mutable

    class Person {

      val id = 0
      val firstName: String = "Donald"  // public by default
      val lastName: String = "Trump"   // public by default
      private var age: Int = 0

      def fullName1(): String = {
        //return lastName + ", " + firstName
        lastName + ", " + firstName
      }

      def fullName2(): String = lastName + ", " + firstName

      def birthYear(): Int = age

      // class can access private member of companion singleton object
      def getNumOfHeads(): Int = Person.numOfHeads
    }

    object Person {

      private val numOfHeads: Int = 1
      private val numOfArms: Int = 2
      private val numOfLegs: Int = 2

      private val roster = mutable.Map.empty[String, Int]

      def getNumOfHeads(): Int = numOfHeads

      def updateRoster(fullName: String, age: Int): Unit = {
        if (!roster.contains(fullName))
          roster += (fullName -> age)
      }

      def printRoster(): Unit = {
        for (p <- roster) println(p._1, p._2)
      }
    }

    val p = new Person
    println(p.lastName + ", " + p.firstName)
    //println(p.age)  // cannot access private field
    println(p.fullName2())

    println(Person.getNumOfHeads())
    println(p.getNumOfHeads())

    val p0 = Person
    println(p0.getNumOfHeads())

    p0.updateRoster(p.fullName1(), p.birthYear)

    p0.printRoster()
  }

  def testScalaBasicTypesAndOperations(): Unit = {

    // Integer literal
    val hex = 0x00FF



  }

  // old samples
  def testScalaIterator(args: Array[String]): Unit = {

    import scala.io.Source

    if (args.length > 0) {
      val lines = Source.fromFile(args(0)).getLines().toList
      for (line <- lines)
        println(line)
    }
    else
      Console.err.println("Please enter filename")

  }

  def testJavaList(): Unit = {

      import java.util.Arrays
      import scala.collection.JavaConverters._

      val javaList = Arrays.asList(1,2,3,4)
      println(javaList)

      val scalaList = javaList.asScala
      println(scalaList)

      val javaListAgain = scalaList.asJava

      assert( javaList eq javaListAgain)

  }

  def testJavaBeanStyleClass(): Unit = {

      // normal class
      class Company(var name:String)
      val company = new Company("Google")
      println(company.name)
      company.name = "Amazon"
      println(company.name)

      // normal class
      class Customer (var customerId: Int, var zip: String) {

          def getCustomerId() = customerId
          def setCustomerId(cust: Int): Unit = {
              customerId = cust
          }

      }

      // JavaBean style class
      import scala.beans.BeanProperty

      class CompanyBean(@BeanProperty var name:String)
      val companyBean = new CompanyBean("Google")

      println(companyBean.getName())
      companyBean.setName("Amazon")

      println(companyBean.getName())

  }

  def testScalaTrait(): Unit = {

      class Customer(val name:String, val discountCode:String="N") {
          def discounts() : List[Int] = List(5)
          override def toString() = "Applied discounts:" + discounts.mkString(" ","%, ","% ")
      }

      trait VIPCustomer extends Customer {
          override def discounts = super.discounts ::: List(10)
      }

      trait GoldCustomer extends Customer {
          override def discounts = if (discountCode.equals("H")) super.discounts ::: List(20) else super.discounts ::: List(15)
      }

      val myDiscounts = new Customer("Thomas","H") with VIPCustomer with GoldCustomer
      println(myDiscounts)

  }

  def testScalaCaseClass(): Unit = {

      /*  This is similar to
          object Customer {
              def apply()= new Customer("default name")
          }
          class Customer(name:String) {...}
       */
      case class Customer(val name:String)

      val thomas = Customer("Thomas")
      //val thomas = new Customer("Thomas")

      class Customer2(val name:String)
      //val jerry = Customer2("Jerry") // Not a case (companion) class, you have to use 'new' keyword
  }

  def testScalaException(): Unit = {

      case class Failure(val reason: String)

      def parse(numberAsString: String) : Either[Failure,Int] = {
          try {
              val result = Integer.parseInt(numberAsString)
              Right(result)
          } catch {
              case _: Throwable => Left(Failure("Error when parsing number"))
          }
      }

      println(parse("123"))
      println(parse("123ab"))

  }

  def testScalaForExpression(): Unit = {

      for {
          elem <- List(1,2,3,4,5)
      } yield "T" + elem

      for {
          word <- List("Hello","Scala")
          char <- word
      } yield char.isLower

      for {
          word <- List("Hello","Scala")
          char <- word if char.isUpper
      } yield char

      for {
          word <- List("Hello","Scala")
          char <- word
          lowerChar = char.toLower
      } yield lowerChar

  }

  def testOtherBasics(): Unit = {

    val numbers = List(1,2,3,4,5,6,7,8,9)

    numbers.foreach { n:Int =>
      println(n)
    }

    println(numbers.map(x=> x+1))

    def decrement = (x:Int)=>x-1
    println(numbers.map(decrement))
    println(numbers map decrement)

    //val sumOfNumbers = numbers.sum
    val sumOfNumbers = numbers.foldLeft(0) { (total, element) =>
      total + element
    }

    println(sumOfNumbers)

    /*
    val amounts = List(
        Money(10,"USD"),
        Money(2,"EUR"),
        Money(20,"GBP"),
        Money(75,"EUR"),
        Money(100,"USD"),
        Money(50,"USD")
    )

    //println(amounts)

    //println(amounts.head)
    //println(amounts.tail)

    //val euros = amounts.filter(money => money.currency=="EUR")
    //val euros = amounts.filter(_.currency=="EUR")
    //val usds = amounts.filterNot(_.currency=="EUR")

    // TODO: how to write in this syntax
    //val euros = amounts.filter { m:Money =>
    //    return m.currency == "EUR"
    //}

    //println(euros)
    //println(usds)

    // result of partition is a tuple
    //val euroAndUsd = amounts.partition(money => money.currency=="EUR")
    //println(euroAndUsd._1)
    //println(euroAndUsd._2)

    //val (euros, usds) = amounts.partition(money => money.currency=="EUR")
    //println(euros)
    //println(usds)

    //val amountsMapped = amounts.map(money => Money(money.amount+1, money.currency))

    //println(amounts)
    //println(amountsMapped)

    //val printedAmounts = amounts map(m=> s"${m.amount} ${m.currency}")
    //println(printedAmounts)

    //val sortedAmount = amounts.groupBy(_.currency)
    //println(sortedAmount)
    */

    /*
    val wallet = Map("USD"->10, "EUR"->2)
    println(wallet("EUR"))
    println(wallet.get("EUR"))
    val newWallet = wallet + ("GBP"->20)
    println(wallet)
    println(newWallet)

    val status = wallet.get("EUR") match {
        case None => "Nothing of that currency"
        case Some(value) => "I have "+value+" Euros"
    }

    println(status)
    */

    // String Interpolation
    val name = "Yingqi"
    val amount = 10000.2345
    val hello1 = s"Hello, my name is $name, and I have $amount"
    val hello2 = f"Hello, my name is $name, and I have ${amount*2}%12.2f"
    println(hello1)
    println(hello2)


  }

}
