package local.scala.test

import org.scalatest._

abstract class FlatUnitSpec extends FlatSpec with Matchers with
  OptionValues with Inside with Inspectors

abstract class FunSuiteUnitSpec extends FunSuite with Matchers with
  OptionValues with Inside with Inspectors

abstract class ProUnitSpec extends PropSpec with Matchers with
  OptionValues with Inside with Inspectors

abstract class FeatureUnitSpec extends FeatureSpec with Matchers with
  OptionValues with Inside with Inspectors

