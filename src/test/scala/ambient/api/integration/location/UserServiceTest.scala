package ambient.api.integration.location

import org.scalatest.{BeforeAndAfterEach, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import ambient.api.config.Dependencies.{db, userService => service}
import ambient.api.functional.MongoHelpers._
import com.mongodb.casbah.Imports._
import ambient.api.user.User

class UserServiceTest extends FunSpec with ShouldMatchers with BeforeAndAfterEach {

  private implicit val collection = db("users")

  private val USER = User("The Hoff")

  private val FBID = "42"

  override def beforeEach() {
    clearCollection()
  }

  describe("finding a user") {
    it("should find a user by her facebook id (fbid)") {
      val id = insert(Map("name" -> USER.name, "fbid" -> FBID))
      val user = service.search(FBID)
      user.id.get should be(id.toString)
    }
  }

}
