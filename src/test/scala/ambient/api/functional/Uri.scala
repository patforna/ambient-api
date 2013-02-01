package ambient.api.functional

import scala.language.implicitConversions

object Uri {

  implicit def uriToString(uri: Uri): String = uri.toString

  val CheckinsUri = Uri("/checkins")
  val SearchNearbyUri = Uri("/search/nearby")
  val UsersSearchUri = Uri("/users/search")
  val UsersUri = Uri("/users")

}

case class Uri(path: String, params: (Any, Any)*) {
  def params(newParams: (Any, Any)*): Uri = Uri(path, (params ++ newParams):_*)

  override def toString: String = {
    if (params.isEmpty)
      path
    else
      path + "?" + (params.map { case (k,v) => k + "=" + v}).mkString("&")
  }
}
