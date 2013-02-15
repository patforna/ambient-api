package ambient.api.functional.user

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

import ambient.api.functional.JsonHelpers._
import ambient.api.user.User
import ambient.api.location.Location

class UserToJsonTest extends FunSpec with ShouldMatchers {

  it("should serialise all user fields") {
    val id = "1"
    val first = "foo"
    val last = "bar"
    val fbid = "42"
    val location = Location(1.1, 2.2)
    val picture = "http://.../fish.png"

    val user = User(Some(id), first, last, Some(fbid), Some(location), Some(picture))

    serialize(user) should be(json( s""" { "id" : "$id", "first" : "$first", "last" : "$last", "fbid" : "$fbid", "location" : [1.1, 2.2] , "picture" : "$picture" } """))
  }

}

