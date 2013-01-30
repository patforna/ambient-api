package ambient.api.functional.location

import ambient.api.functional.FunctionalSpec
import ambient.api.config.Dependencies._
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.JsonHelpers._
import ambient.api.functional.Uri._

class CheckinTest extends FunctionalSpec {

  private implicit val collection = db("users")

  describe("check in") {
    it("should update location of user on checkin") {
      given(thereAreSomeUsersInTheSystem)
      when(aUserChecksIn("Jae Lee", "42.2,18.8"))
      then(theUsersLocationShouldHaveBeenUpdatedTo("Jae Lee", "42.2,18.8"))
    }
  }

  private def thereAreSomeUsersInTheSystem {
    clearCollection()
    insert(""" { "name": "Marc Hofer", "location": [-0.099392,51.531974]} """)
    insert(""" { "name": "Jae Lee", "location": [-0.136677,51.537731] } """)
  }

  private def aUserChecksIn(user: String, location: String) {
    post(CheckinsUri.params("location" -> location))(status should be (200))
  }

  private def theUsersLocationShouldHaveBeenUpdatedTo(user: String, location: String) {
    iSearchForUsersNear(location)
    theResponseShouldInclude( s""" { "user" : { "name" : "$user" }, "distance" : 0 }  """)
  }

  private def iSearchForUsersNear(location: String) {
    responseBody = getResponse(SearchNearbyUri.params("location" -> location))
  }

  private def theResponseShouldInclude(s: String) {
    json(responseBody \ "nearby") should include(json(s))
  }
}
