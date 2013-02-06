package ambient.api.user

import com.mongodb.casbah.Imports._
import ambient.api.location.Location
import ambient.api.config.Keys
import ambient.api.config.Keys._

class UserMapper {

  def map(doc: DBObject): User = {
    val id = Some(doc.as[ObjectId](Id).toString)
    val first = doc.as[String](First)
    val last = doc.as[String](Last)
    val fbid = doc.getAs[String](Fbid)
    val location = reverse(doc.getAs[Seq[Double]](Keys.Location)) // TODO pull out location mapper

    User(id, first, last, fbid, location)
  }

  private def reverse(location: Option[Seq[Double]]): Option[Location] = location match {
    case Some(Seq(long, lat)) => Some(Location(lat, long))
    case _ => None
  }
}




