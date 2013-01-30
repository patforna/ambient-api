package ambient.api.integration.location

import org.scalatest.{BeforeAndAfterEach, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import ambient.api.config.Dependencies.{db, checkinService => service}
import ambient.api.location.Location
import ambient.api.functional.MongoHelpers._
import ambient.api.user.User

class CheckinServiceTest extends FunSpec with ShouldMatchers with BeforeAndAfterEach {

  private implicit val collection = db("users")

  private val LOCATION = Location(11.1, 22.2)

  override def beforeEach {
    clearCollection()
  }

  it("should update a users location (and reverse lat/long to keep mongo happy)") {
    val id = insert( """ { "name" : "The Hoff", "location" : [0,0] } """)

    service.checkin(User("The Hoff"), LOCATION)

    (findOneById(id) \ "location").values should be(List(LOCATION.longitude, LOCATION.latitude))
  }

}
