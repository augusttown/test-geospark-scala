package local.scala.test

import org.scalatest.selenium.HtmlUnit

class BasicSeleniumFlatSpec extends FlatUnitSpec with HtmlUnit {

  val host = "http://www.google.com"

  "google home page" should "have text 'google' in title" in {
    go to (host)
    pageTitle should be ("Google")
  }

}
