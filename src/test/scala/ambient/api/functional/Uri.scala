package ambient.api.functional

object Uri {

  implicit def uriToString(uri: Uri): String = uri.toString

  val CheckinsUri = Uri("/checkins")
  val SearchNearbyUri = Uri("/search/nearby")
}

case class Uri(path: String, params: (Any, Any)*) {
  def params(newParams: (Any, Any)*): Uri = Uri(path, (params ++ newParams):_*)

  override def toString: String = {
    params match {
      case p if p.isEmpty => path
      case _ => path + "?" + (params.map { case (k,v) => k + "=" + v}).mkString("&")
    }
  }
}
