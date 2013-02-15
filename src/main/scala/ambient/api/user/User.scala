package ambient.api.user

import ambient.api.location.Location
import ambient.api.config.Keys._
import ambient.api.config.Keys


object User {

  def from(params: Map[String, String]): User = User(None, params(First), params(Last), Some(params(Fbid)), None, Some(params(Picture)))

}

// TODO should fbid be even in here? -> Yes, as long as we use User object to create users...
case class User(id: Option[String], first: String, last: String, fbid: Option[String], location: Option[Location], picture: Option[String]) {
  def toMap: Map[String, Any] = Map(Id -> id, First -> first, Last -> last, Fbid -> fbid, Keys.Location -> location, Picture -> picture)
}
