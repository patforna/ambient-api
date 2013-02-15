package ambient.api.integration.location

import org.scalatest.{BeforeAndAfterEach, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import ambient.api.config.Dependencies.{db, checkinService => service}
import ambient.api.location.Location
import ambient.api.functional.MongoHelpers._
import ambient.api.user.UserBuilder
import com.mongodb.casbah.Imports._
import ambient.api.config.Keys
import ambient.api.config.Keys._

class CheckinServiceTest extends FunSpec with ShouldMatchers with BeforeAndAfterEach {

  private implicit val collection = db("users")
  private val checkins = db("checkins")

  private val LOCATION = Location(1.0, 1.1)
  private val LOCATION_TWO = Location(2.0, 2.1)
  private val LOCATION_THREE = Location(3.0, 3.1)

  override def beforeEach() {
    clearCollection()
    clearCollection()(checkins)
  }

  describe("checkin") {
    it("should update a users location (and reverse lat/long to keep mongo happy)") {
      val userId = insert(UserBuilder().location(Location(0, 0)).build)

      service.checkin(userId.toString, LOCATION)

      val location = collection.findOneByID(userId) map { x => Location(x.as[Seq[Double]](Keys.Location)) }
      location.get should be(LOCATION)
    }

    it("should maintain a history of checkins") {
      val userId = insert(First -> "Fish", Last -> "Blub")
      val anotherUserId = insert(First -> "Cat", Last -> "Meow")

      service.checkin(userId.toString, LOCATION)
      service.checkin(anotherUserId.toString, LOCATION_TWO)
      service.checkin(userId.toString, LOCATION_THREE)

      val results = checkins.find(Map(UserId -> userId)).toList
      val locations = results map { x => Location(x.as[Seq[Double]](Keys.Location)) }
      locations should be(List(LOCATION, LOCATION_THREE))
    }

  }
}
