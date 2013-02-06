package ambient.api.user

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class UserTest extends FunSpec with ShouldMatchers {

  val required = Map("first" -> "first", "last" -> "last")
  val optional = Map("fbid" -> "fbid")

  describe("creating a user from key/value pairs") {

    it("should populate first, last and fbid fields") {
      User.from(required ++ optional) should be(User(None, "first", "last", Some("fbid"), None))
    }

    it("should blow up if first name is missing") {
      intercept[NoSuchElementException] { User.from(required - "first") }
    }

    it("should blow up if last name is missing") {
      intercept[NoSuchElementException] { User.from(required - "last") }
    }

    it("shouldn't mind if optional fields (e.g. fbid) are missing") {
      User.from(required) should be(User(None, "first", "last", None, None))
    }

  }

}
