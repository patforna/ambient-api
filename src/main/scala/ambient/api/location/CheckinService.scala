package ambient.api.location

import ambient.api.user.User

import com.mongodb.casbah.Imports._
import org.joda.time.DateTime

import com.mongodb.casbah.commons.conversions.scala._
import ambient.api.config.Keys
import ambient.api.config.Keys.{First, Last}

class CheckinService(db: MongoDB) {

  RegisterJodaTimeConversionHelpers()

  private val users = db("users")
  private val checkins = db("checkins")

  def checkin(user: User, location: Location) {
    users.update(Map(First -> user.first, Last -> user.last), $set(List(Keys.Location ->(location.longitude, location.latitude))))
    checkins.insert(Map(First -> user.first, Last -> user.last, Keys.Location -> List(location.longitude, location.latitude), "time" -> DateTime.now))      // FIXME use user.id
  }
}
