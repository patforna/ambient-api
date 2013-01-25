package ambient.api.location

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class LocationTest extends FunSpec with ShouldMatchers {

  it("should recognise latitude and longitude") {
    Location("42.0123,8.9876") should be(Location(42.0123, 8.9876))
    Location("13.01,10.2") should be(Location(13.01, 10.2))
    Location("13,10") should be(Location(13, 10))
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
    intercept[IllegalArgumentException](Location("-90.1,0"))
    intercept[IllegalArgumentException](Location("0,180.1"))
    intercept[IllegalArgumentException](Location("0,-180.1"))
  }
}
