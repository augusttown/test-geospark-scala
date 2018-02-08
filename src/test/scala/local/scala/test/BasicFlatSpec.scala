package local.scala.test

class BasicFlatSpec extends FlatUnitSpec {

  "true" should "be true" in {
    assert(true === true)
    true should be (true)

  }

  "empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }

}
