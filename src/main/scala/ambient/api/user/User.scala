package ambient.api.user

object User {

  def apply(first: String, last: String): User = User(None, None, first, last)

  def apply(first: String, last: String, fbid: String): User = User(None, Some(fbid), first, last)

  def from(map: Map[String, String]): User = User(None, Some(map("fbid")), map("first"), map("last"))

}

case class User(id: Option[String], fbid: Option[String], first: String, last: String)
