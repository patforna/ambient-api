package ambient.api.location

import ambient.api.user.User

import com.mongodb.casbah.Imports._
import org.joda.time.DateTime

import com.mongodb.casbah.commons.conversions.scala._

class CheckinService(db: MongoDB) {

  RegisterJodaTimeConversionHelpers()

  private val users = db("users")
  private val checkins = db("checkins")

  def checkin(user: User, location: Location) {
    users.update(Map("name" -> user.name), $set(List("location" ->(location.longitude, location.latitude))))
    checkins.insert(Map("name" -> user.name, "location" -> List(location.longitude, location.latitude), "time" -> DateTime.now))
  }
}
