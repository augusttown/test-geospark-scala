package local.scala.test

// This can be an entry point of an application
/*object TestScalaClassWithMain {
  def main(args: Array[String]): Unit = {}
}*/

object TestScalaApp extends App {
  // args is given by default
  for (arg <- args)
    println(arg)
}


