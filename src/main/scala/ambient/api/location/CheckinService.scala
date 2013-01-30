package ambient.api.location

import ambient.api.user.User
import com.mongodb.casbah.commons.MongoDBObject

import com.mongodb.casbah.Imports._

class CheckinService(db: MongoDB) {

  private val collection = db("users")

  def checkin(user: User, location: Location) {
    collection.update(MongoDBObject("name" -> user.name), $set(Seq("location" -> (location.longitude, location.latitude))))
  }
}
