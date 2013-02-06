package ambient.api.user

import ambient.api.location.Location


object User {

  def apply(first: String, last: String): User = User(None, first, last, None, None)

  def apply(first: String, last: String, fbid: String): User = User(None, first, last, Some(fbid), None)

  def from(map: Map[String, String]): User = User(None, map("first"), map("last"), map.get("fbid"), None)

}

case class User(id: Option[String], first: String, last: String, fbid: Option[String], location: Option[Location])
