package ambient.api.functional.location

import ambient.api.functional.FunctionalSpec
import ambient.api.config.Dependencies._
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.JsonHelpers._
import ambient.api.functional.Uri._
import org.scalatra.test.ClientResponse
import com.mongodb.casbah.Imports._
import ambient.api.location.Location
import ambient.api.user.User

class CheckinTest extends FunctionalSpec {

  private implicit val collection = db("users")
  private val checkins = db("checkins")

  private val Jae = User("Jae", "Lee")

  override def beforeEach() {
    clearCollection()
    clearCollection()(checkins)
  }

  describe("check in") {
    it("should update the location of the user who checks in") {
      given(thereAreSomeUsersInTheSystem)
      when(aUserChecksIn(Jae, "42.2,18.8"))
      `then`(theUsersLocationShouldHaveBeenUpdatedTo(Jae, "42.2,18.8"))
    }

    it("should maintain a history of all checkins") {
      given(thereAreSomeUsersInTheSystem)

      when(aUserChecksIn(Jae, "1,0"))
      //      when(aUserChecksIn("Marc Hofer", "1,0")) // FIXME add when we've implemented a way to distinguish users
      //      when(aUserChecksIn("Marc Hofer", "2,0")) // FIXME add when we've implemented a way to distinguish users
      when(aUserChecksIn(Jae, "2,0"))
      when(aUserChecksIn(Jae, "3,0"))

      `then`(theUsersCheckinHistoryShouldBe(Jae, List("1,0", "2,0", "3,0")))
    }
  }

  private def thereAreSomeUsersInTheSystem {
    insert("first" -> "Marc", "last" -> "Hofer", "location" ->(-0.099392, 51.531974))
    insert("first" -> "Jae", "last" -> "Lee", "location" ->(-0.136677, 51.537731))
  }

  private def aUserChecksIn(user: User, location: String) {
    // FIXME use user once FB login is implemented
    val response: ClientResponse = post(CheckinsUri.params("location" -> location))
    response.status should be(200)
    response.body should be('empty)
  }

  private def theUsersLocationShouldHaveBeenUpdatedTo(user: User, location: String) {
    iSearchForUsersNear(location)
    theResponseShouldInclude( s""" { "user" : { "first" : "${user.first}", "last" : "${user.last}" }, "distance" : 0 }  """)
  }

  private def iSearchForUsersNear(location: String) {
    get(SearchNearbyUri.params("location" -> location))(asJson)
  }

  private def theResponseShouldInclude(s: String) {
    json(responseJson \ "nearby") should include(json(s))
  }

  private def theUsersCheckinHistoryShouldBe(user: User, locations: List[String]) {
    val results = checkins.find(Map("first" -> user.first, "last" -> user.last)).sort(Map("timestamp" -> -1)).toList
    val actual = results map { _.as[MongoDBList]("location") } map { loc => Location((loc(1)).asInstanceOf[Double], ((loc(0)).asInstanceOf[Double])) }

    val expected = locations map { Location(_) }
    actual should be(expected)
  }
}
