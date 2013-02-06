package ambient.api.integration.user

import org.scalatest.{BeforeAndAfterEach, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import ambient.api.config.Dependencies.{db, userService => service}
import ambient.api.functional.MongoHelpers._
import com.mongodb.casbah.Imports._
import ambient.api.user.User
import ambient.api.platform.NotFoundException
import ambient.api.config.Keys._
import com.mongodb.MongoException.DuplicateKey

class UserServiceTest extends FunSpec with ShouldMatchers with BeforeAndAfterEach {

  private implicit val collection = db("users")

  private val USER = User("The", "Hoff", "42")

  override def beforeEach() {
    clearCollection()
  }

  describe("finding a user") {
    it("should find a user by her facebook id (fbid)") {
      val id = insert(First -> USER.first, Last -> USER.last, Fbid -> USER.fbid.get)
      val user = service.search(USER.fbid.get)
      user.id.get should be(id.toString)
    }

    it("should blow up if fbid can't be found") {
      intercept[NotFoundException] { service.search("not-there") }
    }
  }

  describe("creating a user") {
    it("should create a user given (first, last, fbid)") {
      service.create(USER)
      val user = service.search(USER.fbid.get)
      user.first should be (USER.first)
      user.last should be (USER.last)
    }

    it("should return the created user") {
      val user = service.create(USER)
      service.search(USER.fbid.get).id should be (user.id)
    }

    it("should blow up if fbid already exists") {
      service.create(USER)
      intercept[IllegalStateException] { service.create(USER) }
    }
  }

}
