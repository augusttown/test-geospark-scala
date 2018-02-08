package local.scala.test

import org.scalatest._
import org.scalatest.prop.TableDrivenPropertyChecks
import prop._
import scala.collection.immutable._

class BasicProSpec extends ProUnitSpec with TableDrivenPropertyChecks {

  val examples =
    Table(
      "set",
      BitSet.empty,
      HashSet.empty[Int],
      TreeSet.empty[Int]
    )

  property("an empty Set should have size 0") {
    forAll(examples) { set =>
      set.size should be (0)
    }
  }

  property("invoking head on an empty set should produce NoSuchElementException") {
    forAll(examples) { set =>
      a [NoSuchElementException] should be thrownBy { set.head }
    }
  }

}
