package ambient.api.user

object User {
  def apply(name: String): User = User(None, name)
}

case class User(id: Option[String], name: String)
