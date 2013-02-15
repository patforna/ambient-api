package ambient.api.functional.search

import ambient.api.functional.FunctionalSpec
import ambient.api.config.Dependencies._
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.JsonHelpers._
import ambient.api.functional.Uri._
import ambient.api.user.UserBuilder
import ambient.api.location.Location
import ambient.api.config.Keys

class SearchTest extends FunctionalSpec {

  val user = UserBuilder().first("Jae").last("Lee").fbid("2").location(Location(51.537731, -0.136677)).build

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
      `then`(theResponseShouldInclude( """ { "user" : { "first" : "Jae", "last": "Lee" }, "distance" : 2550 }  """))
    }
  }

  private def thereAreSomeUsersInTheSystem {
    insert(UserBuilder().fbid("1").location(Location(51.554093, -0.104514)).build)
    insert(user)
    insert(UserBuilder().fbid("3").location(Location(51.531974, -0.099392)).build)
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
