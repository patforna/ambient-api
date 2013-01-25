package ambient.api.functional.search

import org.json4s.jackson.JsonMethods._
import org.json4s.JsonAST.JValue
import ambient.api.functional.FunctionalSpec

import org.scalatra.servlet.ScalatraListener

class SearchTest extends FunctionalSpec {

  private var responseBody: JValue = _

  describe("search nearby") {
    it("should bark if no or invalid location has been specified") {
      getStatus("/search/nearby") should be(400)
      getStatus("/search/nearby?location=") should be(400)
      getStatus("/search/nearby?location=foo,bar") should be(400)
    }

    it("should find nearby users") {
      given(thereAreSomeUsersInTheSystem)
      when(iSearchForUsersNear("51.515874,-0.125613"))
      then(theResponseShouldInclude( """ { "user" : { "name" : "Jae Lee" }, "distance" : 2550 }  """))
    }
  }

  private def thereAreSomeUsersInTheSystem {
    // TODO implement
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
      status should equal(200)
      response.mediaType should equal(Some("application/json"))
      parse(body)
    }
  }
}
