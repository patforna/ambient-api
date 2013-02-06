package ambient.api.user

import com.mongodb.casbah.Imports._
import ambient.api.platform.NotFoundException
import ambient.api.config.Keys._

class UserService(db: MongoDB, userMapper: UserMapper) {

  private val users = db("users")

  def create(user: User): User = {
    val doc: DBObject = Map(First -> user.first, Last -> user.last, Fbid -> user.fbid)
    users.insert(doc)
    userMapper.map(doc) // TODO unit test
  }

  def search(fbid: String): User = {
    users.findOne(Map(Fbid -> fbid)) match {
      case Some(doc) => userMapper.map(doc) // TODO unit test
      case _ => throw new NotFoundException(s"Can't find user with fbid '$fbid'")
    }
  }

}
