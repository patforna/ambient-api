package ambient.api.functional.location

import ambient.api.functional.FunctionalSpec
import ambient.api.config.Dependencies._
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.JsonHelpers._
import ambient.api.functional.Uri._
import org.scalatra.test.ClientResponse
import com.mongodb.casbah.Imports._
import ambient.api.location.Location
import ambient.api.config.Keys
import ambient.api.config.Keys._
import util.Random

class CheckinTest extends FunctionalSpec {

  implicit val collection = db("users")

  val checkins = db("checkins")

  val userId = new ObjectId().toString

  val anotherUserId = new ObjectId().toString

  val required = Map(UserId -> userId, Keys.Location -> "1.0,2.0")

  override def beforeEach() {
    clearCollection()
    clearCollection()(checkins)
  }

  describe("check in") {
    it("should update the location of the user who checks in") {
      given(aUserWith(userId))
      and(aUserWith(anotherUserId))
      when(aUserChecksIn(userId, "42.2,18.8"))
      `then`(theUsersLocationShouldHaveBeenUpdatedTo(userId, "42.2,18.8"))
    }

    it("should maintain a history of all checkins") {
      given(aUserWith(userId))
      and(aUserWith(anotherUserId))

      when(aUserChecksIn(userId, "1,0"))
      when(aUserChecksIn(anotherUserId, "1,0"))
      when(aUserChecksIn(anotherUserId, "2,0"))
      when(aUserChecksIn(userId, "2,0"))
      when(aUserChecksIn(userId, "3,0"))

      `then`(theUsersCheckinHistoryShouldBe(userId, List("1,0", "2,0", "3,0")))
    }

    it("should 400 if userid missing") {
      post(CheckinsUri.params((required - UserId).toSeq:_*))(statusCode) should be (400)
    }

    it("should 400 if location missing") {
      post(CheckinsUri.params((required - Keys.Location).toSeq:_*))(statusCode) should be (400)
    }
  }

  private def aUserWith(id: String) {
    insert(Id -> new ObjectId(id), First -> "Marc", Last -> "Random", Keys.Location ->(-0.099392, 51.531974), Fbid -> Random.nextInt.toString) // TODO could do with a UserDocBuilder
  }

  private def aUserChecksIn(id: String, location: String) {
    val response: ClientResponse = post(CheckinsUri.params(UserId -> id, Keys.Location -> location))
    response.status should be(200)
    response.body should be('empty)
  }

  private def theUsersLocationShouldHaveBeenUpdatedTo(userId: String, location: String) {
    val Array(lat, long) = location.split(",")
    iSearchForUsersNear(location)
    ((responseJson \ "nearby")(0) \\ "location").extract[List[Double]] should be (List(lat.toDouble, long.toDouble))
  }

  private def iSearchForUsersNear(location: String) {
    get(SearchNearbyUri.params(Keys.Location -> location))(asJson)
  }

  private def theUsersCheckinHistoryShouldBe(userId: String, locations: List[String]) {
    val results = checkins.find(Map(UserId -> new ObjectId(userId))).toList
    val actual = results map { _.as[MongoDBList](Keys.Location) } map { loc => Location((loc(1)).asInstanceOf[Double], ((loc(0)).asInstanceOf[Double])) }

    val expected = locations map { Location(_) }
    actual should be(expected)
  }
}
