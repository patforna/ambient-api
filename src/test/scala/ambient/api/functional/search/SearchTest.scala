package ambient.api.functional.search

import ambient.api.functional.FunctionalSpec
import ambient.api.config.Dependencies._
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.JsonHelpers._
import ambient.api.functional.Uri._
import ambient.api.user.User
import ambient.api.location.Location
import ambient.api.config.Keys._
import ambient.api.config.Keys

class SearchTest extends FunctionalSpec {

  val user = User(None, "Jae", "Lee", None, Some(Location(-0.136677, 51.537731)))

  implicit val collection = db("users")

  override def beforeEach() {
    clearCollection()
  }

  describe("search nearby") {
    it("should bark if no or invalid location has been specified") {
      get(SearchNearbyUri)(statusCode) should be(400)
      get(SearchNearbyUri.params(Keys.Location -> ""))(statusCode) should be(400)
      get(SearchNearbyUri.params(Keys.Location -> "foo,bar"))(statusCode) should be(400)
    }

    it("should find nearby users") {
      given(thereAreSomeUsersInTheSystem)
      when(iSearchForUsersNear("51.515874,-0.125613"))
      `then`(theResponseShouldInclude( """ { "user" : { "first" : "Jae", "last": "Lee", "location": [51.537731, -0.136677] }, "distance" : 2550 }  """))
    }
  }

  private def thereAreSomeUsersInTheSystem {
    insert(First -> "Patric", Last -> "Fornasier", Keys.Location ->(-0.104514, 51.554093))
    insert(First -> user.first, Last -> user.last, Keys.Location -> user.location)
    insert(First -> "Marc", Last -> "Hofer", Keys.Location ->(-0.099392, 51.531974))
  }

  private def iSearchForUsersNear(location: String) {
    get(SearchNearbyUri.params(Keys.Location -> location))(asJson)
  }

  private def theResponseShouldInclude(s: String) {
    val ignore = List("id")
    val nearby = (responseJson \ "nearby")(0).removeField { case (k, _) => ignore.contains(k) }
    json(nearby) should include(json(s))
  }
}
