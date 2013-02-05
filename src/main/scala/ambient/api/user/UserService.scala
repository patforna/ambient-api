package ambient.api.user

import com.mongodb.casbah.Imports._
import ambient.api.platform.NotFoundException

class UserService(db: MongoDB) {

  private val users = db("users")

  def create(user: User): User = {
    val doc: DBObject = Map("first" -> user.first, "last" -> user.last, "fbid" -> user.fbid)
    users.insert(doc)
    map(doc)
  }

  def search(fbid: String): User = {
    users.findOne(Map("fbid" -> fbid)) match {
      case Some(x) => map(x)
      case _ => throw new NotFoundException(s"Can't find user with fbid '$fbid'")
    }
  }

  private def map(user: DBObject): User = {         // TODO pull out and unit test
    val id = user.as[ObjectId]("_id").toString
    val first = user.as[String]("first")
    val last = user.as[String]("last")
    val fbid = user.getAs[String]("fbid")

    User(Some(id), fbid, first, last)
  }

}
