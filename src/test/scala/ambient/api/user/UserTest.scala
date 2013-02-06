package ambient.api.user

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import ambient.api.config.Keys._

class UserTest extends FunSpec with ShouldMatchers {

  val required = Map(First -> "FIRST", Last -> "LAST")
  val optional = Map(Fbid -> "FBID")

  describe("creating a user from key/value pairs") {

    it("should populate first, last and fbid fields") {
      User.from(required ++ optional) should be(User(None, "FIRST", "LAST", Some("FBID"), None))
    }

    it("should blow up if first name is missing") {
      intercept[NoSuchElementException] { User.from(required - First) }
    }

    it("should blow up if last name is missing") {
      intercept[NoSuchElementException] { User.from(required - Last) }
    }

    it("shouldn't mind if optional fields (e.g. fbid) are missing") {
      User.from(required) should be(User(None, "FIRST", "LAST", None, None))
    }

  }

}
