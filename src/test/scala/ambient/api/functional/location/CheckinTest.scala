package ambient.api.functional.location

import org.json4s.jackson.JsonMethods._
import org.json4s.JsonAST.JValue
import ambient.api.functional.FunctionalSpec

class CheckinTest extends FunctionalSpec {

  private var responseBody: JValue = _
  describe("check in") {
    it("should update location of user on checkin") {
      given(thereAreSomeUsersInTheSystem)
      when(aUserChecksIn("Jae Lee", "42.2,18.8"))
      then(theUsersLocationShouldHaveBeenUpdatedTo("Jae Lee", "42.2,18.8"))
    }
  }

  private def thereAreSomeUsersInTheSystem {
    // TODO implement
  }

  private def aUserChecksIn(user: String, location: String) {
    post(s"checkins?location=$location")(status should be (200))
  }

  private def theUsersLocationShouldHaveBeenUpdatedTo(user: String, location: String) {
    iSearchForUsersNear(location)
    theResponseShouldInclude( s""" { "user" : { "name" : "$user" }, "distance" : 0 }  """)
  }

  private def iSearchForUsersNear(location: String) {
    responseBody = getResponse("/search/nearby?location=" + location)
  }

  private def theResponseShouldInclude(s: String) {
    json(responseBody \ "nearby") should include(json(s))
  }

  private def json(v: JValue): String = compact(render(v))

  private def json(s: String): String = json(parse(s))

  private def getStatus(path: String) = get(path)(status)

  private def getResponse(path: String) = {
    get(path) {
      status should be(200)
      response.mediaType should be(Some("application/json"))
      parse(body)
    }
  }
}
