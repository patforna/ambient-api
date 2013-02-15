package ambient.api.location

import com.mongodb.casbah.Imports._

import ambient.api.config.Keys
import ambient.api.config.Keys._

class CheckinService(db: MongoDB) {

  private val users = db("users")
  private val checkins = db("checkins")

  def checkin(userId: String, location: Location) {
    users.update(Map(Id -> new ObjectId(userId)), $set(List(Keys.Location -> location.reverse)))
    checkins.insert(Map(UserId -> new ObjectId(userId), Keys.Location -> location.reverse))
  }
}
