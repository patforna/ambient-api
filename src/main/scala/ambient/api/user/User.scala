package ambient.api.user

import ambient.api.location.Location


object User {

  def apply(first: String, last: String): User = User(None, None, first, last, None)

  def apply(first: String, last: String, fbid: String): User = User(None, Some(fbid), first, last, None)

  def from(map: Map[String, String]): User = User(None, map.get("fbid"), map("first"), map("last"), None)

}

case class User(id: Option[String], fbid: Option[String], first: String, last: String, location: Option[Location])
