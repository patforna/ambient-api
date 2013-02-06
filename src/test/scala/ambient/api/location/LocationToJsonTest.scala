package ambient.api.location

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import ambient.api.functional.JsonHelpers._

class LocationToJsonTest extends FunSpec with ShouldMatchers {

  it("should serialise to array") {
    serialize(Location(2.2, 3.3)) should be(json("[2.2,3.3]"))
  }

}

