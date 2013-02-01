package ambient.api.functional.location

import ambient.api.functional.FunctionalSpec
import ambient.api.config.Dependencies._
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.JsonHelpers._
import ambient.api.functional.Uri._
import org.scalatra.test.ClientResponse
import com.mongodb.casbah.Imports._
import ambient.api.location.Location


class CheckinTest extends FunctionalSpec {

  private implicit val collection = db("users")
  private val checkins = db("checkins")

  override def beforeEach {
    clearCollection()
    clearCollection()(checkins)
  }

  describe("check in") {
    it("should update the location of the user who checks in") {
      given(thereAreSomeUsersInTheSystem)
      when(aUserChecksIn("Jae Lee", "42.2,18.8"))
      `then`(theUsersLocationShouldHaveBeenUpdatedTo("Jae Lee", "42.2,18.8"))
    }

    it("should maintain a history of all checkins") {
      given(thereAreSomeUsersInTheSystem)

      when(aUserChecksIn("Jae Lee", "1,0"))
//      when(aUserChecksIn("Marc Hofer", "1,0")) // FIXME add when we've implemented a way to distinguish users
//      when(aUserChecksIn("Marc Hofer", "2,0")) // FIXME add when we've implemented a way to distinguish users
      when(aUserChecksIn("Jae Lee", "2,0"))
      when(aUserChecksIn("Jae Lee", "3,0"))

      `then`(theUsersCheckinHistoryShouldBe("Jae Lee", List("1,0", "2,0", "3,0")))
    }
  }

  private def thereAreSomeUsersInTheSystem {
    insert(""" { "name": "Marc Hofer", "location": [-0.099392,51.531974]} """)
    insert(""" { "name": "Jae Lee", "location": [-0.136677,51.537731] } """)
  }

  private def aUserChecksIn(user: String, location: String) {
    val response: ClientResponse = post(CheckinsUri.params("location" -> location))
    response.status should be (200)
    response.body should be ('empty)
  }

  private def theUsersLocationShouldHaveBeenUpdatedTo(user: String, location: String) {
    iSearchForUsersNear(location)
    theResponseShouldInclude( s""" { "user" : { "name" : "$user" }, "distance" : 0 }  """)
  }

  private def iSearchForUsersNear(location: String) {
    get(SearchNearbyUri.params("location" -> location))(asJson)
  }

  private def theResponseShouldInclude(s: String) {
    json(responseJson \ "nearby") should include(json(s))
  }

  private def theUsersCheckinHistoryShouldBe(user: String, locations: List[String]) {
    val results = checkins.find(Map("name" -> user)).sort(Map("timestamp" -> -1)).toList
    val actual = results map { _.as[MongoDBList]("location") } map { loc => Location((loc(1)).asInstanceOf[Double], ((loc(0)).asInstanceOf[Double])) }

    val expected = locations map { Location(_) }
    actual should be(expected)
  }
}
