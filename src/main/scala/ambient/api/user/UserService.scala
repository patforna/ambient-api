package ambient.api.user

import com.mongodb.casbah.Imports._

class UserService(db: MongoDB) {

  private val users = db("users")

  def search(fbid: String): User = {
    users.findOne(Map("fbid" -> fbid)) match {
      case Some(x) => map(x)
      case _ => throw new IllegalArgumentException("not found") // TODO
    }
  }

  def map(user: DBObject): User = {
    val id = user.as[ObjectId]("_id").toString
    val name = user.as[String]("name")

    User(Some(id), name)
  }

}
