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

  override def beforeEach() {
    clearCollection()
  }

  describe("finding a user") {

    it("should find a user by her Facebook id (fb)") {
      val id = insert(Map("name" -> name, "fbid" -> fb))
      get(UsersSearchUri.params("fb" -> fb))(asJson)
      (responseJson \ "user" \ "id").extract[String] should be(id.toString)
    }

    it("should return 404 if no user with given Facebook id found") {
      get(UsersSearchUri.params("fb" -> "does-not-exist"))(statusCode) should be (404)
    }

  }

  describe("creating a user") {

    it("should create a user using (fb, first, last)") {
      pending
      post(UsersUri.params("fb" -> fb, "first" -> first, "last" -> last))

      // todo check response from POST

      get(UsersSearchUri.params("fb" -> fb))(asJson)

      json(responseJson \ "user" \ "name") should be(name)
    }

  }


}
