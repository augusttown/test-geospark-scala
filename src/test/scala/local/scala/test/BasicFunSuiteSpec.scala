package local.scala.test

import org.scalatest.FunSuite

class BasicFunSuiteSpec extends FunSuiteUnitSpec {
  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}
