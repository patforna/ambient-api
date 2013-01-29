package ambient.api.location

import ambient.api.user.User
import com.mongodb.casbah.commons.MongoDBObject

import com.mongodb.casbah.Imports._

class CheckinService(db: MongoDB) {

  private val USER_COLLECTION = "users"

  def checkin(user: User, location: Location) {
    val mongo = db(USER_COLLECTION)
    mongo.update(MongoDBObject("name" -> user.name), $set(Seq("location" -> (location.latitude, location.longitude))))
  }
}
