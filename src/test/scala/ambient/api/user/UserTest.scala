package ambient.api.user

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import ambient.api.config.Keys._

class UserTest extends FunSpec with ShouldMatchers {

  describe("creating a user from key/value pairs") {

    val required = Map(First -> "FIRST", Last -> "LAST", Fbid -> "FBID", Picture -> "PICTURE")

    it("should populate first, last and fbid fields") {
      User.from(required) should be(User(None, "FIRST", "LAST", Some("FBID"), None, Some("PICTURE")))
    }

    it("should blow up if first name is missing") {
      intercept[NoSuchElementException] { User.from(required - First) }
    }

    it("should blow up if last name is missing") {
      intercept[NoSuchElementException] { User.from(required - Last) }
    }

    it("should blow up if fbid is missing") {
      intercept[NoSuchElementException] { User.from(required - Fbid) }
    }

    it("should blow up if picture is missing") {
      intercept[NoSuchElementException] { User.from(required - Picture) }
    }
  }

  describe("turning a user into a map of key/value pairs") {
    it("should put all fields (except id) into a map") {
      val user = UserBuilder().withAllFieldsPopulated.build
      user.asMap should be(Map(First -> user.first, Last -> user.last, Fbid -> user.fbid, Location -> user.location, Picture -> user.picture))
    }

  }
}
