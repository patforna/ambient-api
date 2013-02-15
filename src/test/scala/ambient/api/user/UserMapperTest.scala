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
  val lat = 1.0
  val long = 2.0
  val required = Map(Id -> new ObjectId(id), First -> first, Last -> last)
  var picture = "http://.../foo.jpg"
  val optional = { Map(Keys.Location -> MongoDBList(long, lat), Picture -> picture) }

  val mapper = new UserMapper

  describe("map to user") {

    it("should construct a user") {
      mapper.map(required ++ optional) should be(User(Some(id), first, last, None, Some(Location(lat, long)), Some(picture)))
    }

    it("shouldn't mind if optional fields are missing") {
      mapper.map(required) should be(User(Some(id), first, last, None, None, None))
    }

    it("should not populate fbid (maybe this will change in the future)") {
      mapper.map(required).fbid should be(None)
    }

    it("should blow up if id is missing") {
      intercept[NoSuchElementException] { mapper.map(required - Id) }
    }

    it("should blow up if first name is missing") {
      intercept[NoSuchElementException] { mapper.map(required - First) }
    }

    it("should blow up if last name is missing") {
      intercept[NoSuchElementException] { mapper.map(required - Last) }
    }
  }
}