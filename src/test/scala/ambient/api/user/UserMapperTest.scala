package ambient.api.user

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.TypeImports.ObjectId
import ambient.api.location.Location

class UserMapperTest extends FunSpec with ShouldMatchers {

  val Id = "507f1f77bcf86cd799439011"
  val First = "*first*"
  val Last = "*last*"
  val Fbid = "*fbid"
  val Lat = 1.0
  val Long = 2.0
  val Required = Map("_id" -> new ObjectId(Id), "first" -> First, "last" -> Last)
  val Optional = Map("fbid" -> Fbid, "location" -> MongoDBList(Long, Lat))

  val mapper = new UserMapper

  describe("map to user") {

    it("should construct a user") {
      mapper.map(Required ++ Optional) should be(User(Some(Id), Some(Fbid), First, Last, Some(Location(Lat, Long))))
    }

    it("shouldn't mind if optional fields are missing") {
      mapper.map(Required) should be(User(Some(Id), None, First, Last, None))
    }

    it("should blow up if id is missing") {
      intercept[NoSuchElementException] { mapper.map(Required - "_id") }
    }

    it("should blow up if first name is missing") {
      intercept[NoSuchElementException] { mapper.map(Required - "first") }
    }

    it("should blow up if last name is missing") {
      intercept[NoSuchElementException] { mapper.map(Required - "last") }
    }
  }
}