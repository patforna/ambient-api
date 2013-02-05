package ambient.api.user

import com.mongodb.casbah.Imports._
import ambient.api.platform.NotFoundException

class UserService(db: MongoDB, userMapper: UserMapper) {

  private val users = db("users")

  def create(user: User): User = {
    val doc: DBObject = Map("first" -> user.first, "last" -> user.last, "fbid" -> user.fbid)
    users.insert(doc)
    userMapper.map(doc) // TODO unit test
  }

  def search(fbid: String): User = {
    users.findOne(Map("fbid" -> fbid)) match {
      case Some(doc) => userMapper.map(doc) // TODO unit test
      case _ => throw new NotFoundException(s"Can't find user with fbid '$fbid'")
    }
  }

}
