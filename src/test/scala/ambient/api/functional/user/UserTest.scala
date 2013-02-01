package ambient.api.functional.user

import ambient.api.config.Dependencies.db
import ambient.api.functional.FunctionalSpec
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.JsonHelpers._
import ambient.api.functional.Uri._
import com.mongodb.casbah.Imports._

class UserTest extends FunctionalSpec {

  private val fb = "500"
  private val first = "Stefan"
  private val last = "Fazzlar"
  private val name = s"$first $last"

  private implicit val collection = db("users")

  override def beforeEach {
    clearCollection()
  }

  describe("creating and loading a user") {

    it("should find a user by FB id") {
      pending
      val id = insert(Map("fb_id" -> "500", "name" -> name))

      get(UsersSearchUri.params("fb" -> "5000"))(asJson)

      json(responseJson \ "user" \ "id") should be("Stefan Fazzlar")
      json(responseJson \ "user" \ "name") should be(id)
    }

    it("should create a user using (fb, first, last)") {
      pending
      post(UsersUri.params("fb" -> fb, "first" -> first, "last" -> last))

      get(UsersSearchUri.params("fb" -> fb))(asJson)

      json(responseJson \ "user" \ "name") should be(name)
    }
  }


}
