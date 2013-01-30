package ambient.api.integration.location

import org.scalatest.{BeforeAndAfterEach, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import ambient.api.config.Dependencies.{db, checkinService => service}
import ambient.api.location.Location
import ambient.api.functional.MongoHelpers._
import ambient.api.user.User
import com.mongodb.casbah.Imports._

class CheckinServiceTest extends FunSpec with ShouldMatchers with BeforeAndAfterEach {

  private implicit val collection = db("users")
  private val checkins = db("checkins")

  private val LOCATION = Location(1.0, 1.1)
  private val LOCATION_TWO = Location(2.0, 2.1)
  private val LOCATION_THREE = Location(3.0, 3.1)

  private val USER = User("The Hoff")

  private val ANOTHER_USER = User("The Fazz")

  override def beforeEach {
    clearCollection()
    clearCollection()(checkins)
  }

  describe("checkin") {
    it("should update a users location (and reverse lat/long to keep mongo happy)") {
      val id = insert(s""" { "name" : "${USER.name}", "location" : [0,0] } """)

      service.checkin(USER, LOCATION)

      (findOneById(id) \ "location").values should be(List(LOCATION.longitude, LOCATION.latitude)) // FIXME no need to use json here
    }

    it("should maintain a history of checkins") {
      insert(s""" { "name" : "${USER.name}", "location" : [0,0] } """)
      insert(s""" { "name" : "${ANOTHER_USER.name}", "location" : [0,0] } """)

      service.checkin(USER, LOCATION)
      service.checkin(ANOTHER_USER, LOCATION_TWO)
      service.checkin(USER, LOCATION_THREE)

      val results = checkins.find(Map("name" -> USER.name)).sort(Map("timestamp" -> -1)).toList
      val locations = results map { _.as[MongoDBList]("location") } map { loc => Location((loc(1)).asInstanceOf[Double], ((loc(0)).asInstanceOf[Double])) }

      locations should be (List(LOCATION, LOCATION_THREE))
    }

  }
}
