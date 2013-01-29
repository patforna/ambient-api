package ambient.api.integration.location

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import ambient.api.config.Dependencies.{db, checkinService => service}
import ambient.api.location.Location
import ambient.api.functional.MongoHelpers._
import ambient.api.user.User

class CheckinServiceTest extends FunSpec with ShouldMatchers {

  private implicit val collection = db("users")

  collection.drop

  it("should update a users location") {
    val id = insert(""" { "name" : "The Hoff", "location" : [0,0] } """)

    service.checkin(User("The Hoff"), Location(11.1, 22.2))

    (findOneById(id) \ "location").values should be(List(11.1, 22.2))
  }

}
