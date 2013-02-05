package ambient.api.functional.search

import ambient.api.functional.FunctionalSpec
import ambient.api.config.Dependencies._
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.JsonHelpers._
import ambient.api.functional.Uri._

class SearchTest extends FunctionalSpec {

  private implicit val collection = db("users")

  override def beforeEach() {
    clearCollection()
  }

  describe("search nearby") {
    it("should bark if no or invalid location has been specified") {
      get(SearchNearbyUri)(statusCode) should be (400)
      get(SearchNearbyUri.params("location" -> ""))(statusCode) should be (400)
      get(SearchNearbyUri.params("location" -> "foo,bar"))(statusCode) should be (400)
    }

    it("should find nearby users") {
      given(thereAreSomeUsersInTheSystem)
      when(iSearchForUsersNear("51.515874,-0.125613"))
      `then`(theResponseShouldInclude( """ { "user" : { "first" : "Jae", "last": "Lee" }, "distance" : 2550 }  """))
    }
  }

  private def thereAreSomeUsersInTheSystem {
    insert(""" { "first": "Patric", "last": "Fornasier", "location": [-0.104514,51.554093] } """)
    insert(""" { "first": "Jae", "last": "Lee", "location": [-0.136677,51.537731] } """)
    insert(""" { "first": "Marc", "last": "Hofer", "location": [-0.099392,51.531974]} """)
  }

  private def iSearchForUsersNear(location: String) {
    get(SearchNearbyUri.params("location" -> location))(asJson)
  }

  private def theResponseShouldInclude(s: String) {
    json(responseJson \ "nearby") should include(json(s))
  }
}
