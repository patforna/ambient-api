package ambient.api.user

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.TypeImports.ObjectId
import ambient.api.location.Location
import ambient.api.config.Keys
import ambient.api.config.Keys._


class UserMapperTest extends FunSpec with ShouldMatchers {

  val id = "507f1f77bcf86cd799439011"
  val first = "*first*"
  val last = "*last*"
  val fbid = "*fbid"
  val lat = 1.0
  val long = 2.0
  val req = Map(Id -> new ObjectId(id), First -> first, Last -> last)
  val opt = Map(Fbid -> fbid, Keys.Location -> MongoDBList(long, lat))

  val mapper = new UserMapper

  describe("map to user") {

    it("should construct a user") {
      mapper.map(req ++ opt) should be(User(Some(id), first, last, Some(fbid), Some(Location(lat, long))))
    }

    it("shouldn't mind if optional fields are missing") {
      mapper.map(req) should be(User(Some(id), first, last, None, None))
    }

    it("should blow up if id is missing") {
      intercept[NoSuchElementException] { mapper.map(req - Id) }
    }

    it("should blow up if first name is missing") {
      intercept[NoSuchElementException] { mapper.map(req - First) }
    }

    it("should blow up if last name is missing") {
      intercept[NoSuchElementException] { mapper.map(req - Last) }
    }
  }
}