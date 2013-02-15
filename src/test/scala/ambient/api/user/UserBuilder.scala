package ambient.api.user

import util.Random._
import ambient.api.location.Location

case class UserBuilder(first: String = "FIRST-" + nextString(6), last: String = "LAST-" + nextString(6), fbid: Option[String] = None, location: Option[Location] = None, picture: Option[String] = None) {

  private val id = None

  def first(first: String):UserBuilder = UserBuilder(first, last, fbid, location, picture)

  def last(last: String):UserBuilder = UserBuilder(first, last, fbid, location, picture)

  def fbid(fbid: String):UserBuilder = UserBuilder(first, last, Some(fbid), location, picture)

  def location(location: Location):UserBuilder = UserBuilder(first, last, fbid, Some(location), picture)

  def picture(picture: String):UserBuilder = UserBuilder(first, last, fbid, location, Some(picture))

  def withAllFieldsPopulated: UserBuilder = UserBuilder(first, last, Some("FBID-" + nextString(6)), Some(Location(nextInt(90) + 0.1, nextInt(180) + 0.2)), Some("PICTURE-" + nextString(6)))

  def build: User = User(id, first, last, fbid, location, picture)

}
