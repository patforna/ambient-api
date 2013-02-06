package ambient.api.functional.user

import ambient.api.config.Dependencies.db
import ambient.api.functional.FunctionalSpec
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.Uri._
import ambient.api.user.User
import ambient.api.functional.JsonHelpers._

class UserTest extends FunctionalSpec {

  private val USER = User("Stefan", "Fazzlar", "555")

  private implicit val collection = db("users")

  override def beforeEach() {
    clearCollection()
  }

  describe("finding a user") {

    it("should find a user by her Facebook id (fb)") {
      val id = given(someUserExists(USER))
      when(iSearchForUserWithFbId(USER.fbid.get))
      then(theReturnedUserShouldHaveId(id))
    }

    it("should return 400 if no Facebook id given") {
      get(UsersSearchUri)(statusCode) should be(400)
    }

    it("should return 404 if no user with given Facebook id found") {
      get(UsersSearchUri.params("fbid" -> "does-not-exist"))(statusCode) should be(404)
    }

  }

  describe("creating a user") {

    it("should create a user using (fb, first, last)") {
      when(iCreateANewUser(USER))
      then(theUserShouldExists(USER))
      and(theUserShouldHaveBeenReturnedInTheResponse(USER))
    }

  }

  def someUserExists(user: User): String = {
    someUserExists("first" -> user.first, "last" -> user.last, "fbid" -> user.fbid)
  }

  def someUserExists(keyValues: (String, Any)*): String = {
    insert(keyValues:_*).toString
  }

  def iSearchForUserWithFbId(fbid: String) {
    get(UsersSearchUri.params("fbid" -> fbid))(asJson)
  }

  def theReturnedUserShouldHaveId(id: String) {
    theReturnedUserShouldHaveField("id", id)
  }

  def theReturnedUserShouldHaveField(field: String, value: String) {
    (responseJson \ "user" \ field).extract[String] should be(value)
  }

  def iCreateANewUser(user: User) {
    iCreateANewUser("first" -> user.first, "last" -> user.last, "fbid" -> user.fbid.get)
  }

  def iCreateANewUser(keyValues: (String, Any)*) {
    post(UsersUri.params(keyValues:_*))(asJson)
  }

  def theUserShouldExists(user: User) {
    iSearchForUserWithFbId(user.fbid.get)
    theReturnedUserShouldHaveField("first", user.first)
    theReturnedUserShouldHaveField("last", user.last)
  }

  def theUserShouldHaveBeenReturnedInTheResponse(user: User) {
    theReturnedUserShouldHaveField("first", USER.first)
    theReturnedUserShouldHaveField("last", USER.last)
    theReturnedUserShouldHaveField("fbid", USER.fbid.get)
  }

}
