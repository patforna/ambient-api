package ambient.api.location

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import ambient.api.functional.JsonHelpers._
import org.json4s.DefaultFormats

class LocationToJsonTest extends FunSpec with ShouldMatchers {

  it("should serialise to array") {

    implicit val jsonFormats = DefaultFormats + new LocationSerializer

    import org.json4s.jackson.Serialization.write

    json(write(Location(2.2, 3.3))) should be(json("[2.2,3.3]"))
  }

}

