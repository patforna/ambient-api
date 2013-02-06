package ambient.api.functional.user

import ambient.api.config.Dependencies.db
import ambient.api.functional.FunctionalSpec
import ambient.api.functional.MongoHelpers._
import ambient.api.functional.Uri._
import ambient.api.user.User
import ambient.api.functional.JsonHelpers._
import ambient.api.config.Keys._

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
      get(UsersSearchUri.params(Fbid -> "does-not-exist"))(statusCode) should be(404)
    }

  }

  describe("creating a user") {

    it("should create a user using (first, last, fbid)") {
      when(iCreateANewUser(USER))
      then(theUserShouldExists(USER))
      and(theUserShouldHaveBeenReturnedInTheResponse(USER))
    }

    it("should 400 if one of (first, last, fbid) is missing") {
      val required = Map(First -> "x", Last -> "y", Fbid -> "z")
      post(UsersUri.params((required - First).toSeq:_*))(statusCode) should be (400)
      post(UsersUri.params((required - Last).toSeq:_*))(statusCode) should be (400)
      post(UsersUri.params((required - Fbid).toSeq:_*))(statusCode) should be (400)
    }

    it("should 409 if user with given fbid exists already") {
      val user = Seq(First -> "x", Last -> "y", Fbid -> "z")
      post(UsersUri.params(user:_*))
      post(UsersUri.params(user:_*))(statusCode) should be (409)
    }
  }

  def someUserExists(user: User): String = {
    someUserExists(First -> user.first, Last -> user.last, Fbid -> user.fbid)
  }

  def someUserExists(keyValues: (String, Any)*): String = {
    insert(keyValues:_*).toString
  }

  def iSearchForUserWithFbId(fbid: String) {
    get(UsersSearchUri.params(Fbid -> fbid))(asJson)
  }

  def theReturnedUserShouldHaveId(id: String) {
    theReturnedUserShouldHaveField("id", id)
  }

  def theReturnedUserShouldHaveField(field: String, value: String) {
    (responseJson \ "user" \ field).extract[String] should be(value)
  }

  def iCreateANewUser(user: User) {
    iCreateANewUser(First -> user.first, Last -> user.last, Fbid -> user.fbid.get)
  }

  def iCreateANewUser(keyValues: (String, Any)*) {
    post(UsersUri.params(keyValues:_*))(asJson)
  }

  def theUserShouldExists(user: User) {
    iSearchForUserWithFbId(user.fbid.get)
    theReturnedUserShouldHaveField(First, user.first)
    theReturnedUserShouldHaveField(Last, user.last)
  }

  def theUserShouldHaveBeenReturnedInTheResponse(user: User) {
    theReturnedUserShouldHaveField(First, USER.first)
    theReturnedUserShouldHaveField(Last, USER.last)
  }

}
