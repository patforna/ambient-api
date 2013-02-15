package ambient.api.user

import com.mongodb.casbah.Imports._
import ambient.api.platform.NotFoundException
import ambient.api.config.Keys._
import com.mongodb.MongoException.DuplicateKey

class UserService(db: MongoDB, userMapper: UserMapper) {

  private val users = db("users")

  def create(user: User): User = {
    val doc: DBObject = user.asMap
    try {
      users.insert(doc, WriteConcern.JournalSafe)
    }
    catch {
      case e: DuplicateKey => throw new IllegalStateException(e)
    }
    userMapper.map(doc) // TODO unit test #maybe
  }

  def search(fbid: String): User = {
    users.findOne(Map(Fbid -> fbid)) match {
      case Some(doc) => userMapper.map(doc) // TODO unit test #maybe
      case _ => throw new NotFoundException(s"Can't find user with fbid '$fbid'")
    }
  }



}
