package ambient.api.location

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import ambient.api.location.Location._

class LocationTest extends FunSpec with ShouldMatchers {

  describe("creating a location") {
    it("should blow up if location exceeds lat/long range") {
      intercept[IllegalArgumentException](Location(90.1, 0))
      intercept[IllegalArgumentException](Location(-90.1, 0))
      intercept[IllegalArgumentException](Location(0, 180.1))
      intercept[IllegalArgumentException](Location(0, -180.1))
    }
  }

  describe("creating a location from a string") {

    it("should recognise latitude and longitude") {
      Location("42.0123,8.9876") should be(Location(42.0123, 8.9876))
      Location("13.01,10.2") should be(Location(13.01, 10.2))
      Location("13,10") should be(Location(13, 10))
      Location("+42.0123,+8.9876") should be(Location(42.0123, 8.9876))
      Location("+42.0123,-8.9876") should be(Location(42.0123, -8.9876))
      Location("-42.0123,+8.9876") should be(Location(-42.0123, 8.9876))
      Location("-42.0123,-8.9876") should be(Location(-42.0123, -8.9876))
      Location("0,-0") should be(Location(0, 0))
    }

    it("should recognise negative latitude and longitude") {
      Location("42.01,-8.9") should be(Location(42.01, -8.9))
      Location("-42.01,8.9") should be(Location(-42.01, 8.9))
      Location("-42.01,-8.9") should be(Location(-42.01, -8.9))
    }

    it("shoud cope with whitespace") {
      Location("    -1.01,1.02") should be(Location(-1.01, 1.02))
      Location("2.01,-2.02    ") should be(Location(2.01, -2.02))
      Location("3.01,    -3.02") should be(Location(3.01, -3.02))
    }

    it("should blow up if location doesn't appear valid") {
      intercept[IllegalArgumentException](Location(""))
      intercept[IllegalArgumentException](Location("a,b"))
      intercept[IllegalArgumentException](Location(","))
      intercept[IllegalArgumentException](Location("90.1,0"))
      intercept[IllegalArgumentException](Location("+-20,0"))
    }
  }

  describe("creating a location from a sequence of doubles") {

    it("should extract lat,long (by default in reverse order because mongo stores them as (long, lat)") {
      Location(List(1.1, 2.2)) should be(Location(2.2, 1.1))
    }

    it("should not reverse if told so") {
      Location(List(1.1, 2.2), reverse = false) should be(Location(1.1, 2.2))
    }

    it("should blow up if there are not exactly two") {
      intercept[IllegalArgumentException](Location(List()))
      intercept[IllegalArgumentException](Location(List(1.0)))
      intercept[IllegalArgumentException](Location(List(1.0, 2.0, 3.0)))
    }

    it("should cope with optional input") {
      Location(Some(List(1.1, 2.2))) should be (Some(Location(2.2, 1.1)))
      Location(None) should be (None)
    }

  }

  describe("other location methods") {
    it("should reverse lat/longs (to make mongo happy)") {
      Location(1.1, 2.2).reverse should be((2.2, 1.1))
    }
  }


}
