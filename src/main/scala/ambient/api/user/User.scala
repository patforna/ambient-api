package ambient.api.user

import ambient.api.location.Location
import ambient.api.config.Keys._


object User {

  def apply(first: String, last: String): User = User(None, first, last, None, None)

  def apply(first: String, last: String, fbid: String): User = User(None, first, last, Some(fbid), None)

  def from(map: Map[String, String]): User = User(None, map(First), map(Last), Some(map(Fbid)), None)

}

case class User(id: Option[String], first: String, last: String, fbid: Option[String], location: Option[Location]) // TODO should fbid be even in here?
