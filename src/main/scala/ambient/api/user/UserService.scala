package ambient.api.user

import com.mongodb.casbah.Imports._
import ambient.api.platform.NotFoundException

class UserService(db: MongoDB) {

  private val users = db("users")

  def search(fbid: String): User = {
    users.findOne(Map("fbid" -> fbid)) match {
      case Some(x) => map(x)
      case _ => throw new NotFoundException(s"Can't find user with fbid '$fbid'")
    }
  }

  def map(user: DBObject): User = {
    val id = user.as[ObjectId]("_id").toString
    val name = user.as[String]("name")

    User(Some(id), name)
  }

}
