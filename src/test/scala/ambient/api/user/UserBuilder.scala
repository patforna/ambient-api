package ambient.api.user

import util.Random._
import ambient.api.location.Location

case class UserBuilder(id: Option[String] = None, first: String = "FIRST-" + nextString(6), last: String = "LAST-" + nextString(6), fbid: Option[String] = None, location: Option[Location] = None, picture: Option[String] = None) {

  def id(id: String):UserBuilder = UserBuilder(Some(id), first, last, fbid, location, picture)

  def first(first: String):UserBuilder = UserBuilder(id, first, last, fbid, location, picture)

  def last(last: String):UserBuilder = UserBuilder(id, first, last, fbid, location, picture)

  def fbid(fbid: String):UserBuilder = UserBuilder(id, first, last, Some(fbid), location, picture)

  def location(location: Location):UserBuilder = UserBuilder(id, first, last, fbid, Some(location), picture)

  def picture(picture: String):UserBuilder = UserBuilder(id, first, last, fbid, location, Some(picture))

  def withAllFieldsPopulated: UserBuilder = UserBuilder(Some("ID-" + nextString(6)), first, last, Some("FBID-" + nextString(6)), Some(Location(nextInt(90) + 0.1, nextInt(180) + 0.2)), Some("PICTURE-" + nextString(6)))

  def build: User = User(id, first, last, fbid, location, picture)

}
