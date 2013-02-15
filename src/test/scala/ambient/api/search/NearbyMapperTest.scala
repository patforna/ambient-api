package ambient.api.search

import org.scalatest.{BeforeAndAfterEach, OneInstancePerTest, FunSpec}
import org.scalatest.matchers.ShouldMatchers

import ambient.api.user.{UserBuilder, UserMapper, User}
import com.mongodb.casbah.Imports._

import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

class NearbyMapperTest extends FunSpec with ShouldMatchers with BeforeAndAfterEach with OneInstancePerTest with MockitoSugar {

  val distance = 2551.546
  val userDoc: DBObject = Map("name" -> "whatever")
  val resultDoc: DBObject = Map("dis" -> distance, "obj" -> userDoc)
  val user = UserBuilder().build

  val userMapper = mock[UserMapper]
  val mapper = new NearbyMapper(userMapper)

  override def beforeEach() {
    when(userMapper.map(userDoc)).thenReturn(user)
  }

  describe("map to nearby object") {

    it("should construct a Nearby object") {
      val doc = Map("results" -> MongoDBList(resultDoc, resultDoc))
      mapper.map(doc) should be(List(Nearby(user, distance.toInt), Nearby(user, distance.toInt)))
    }

    it("should return empty list when no results") {
      val doc: DBObject = Map("results" -> MongoDBList())
      mapper.map(doc) should be('empty)
    }
  }
}
