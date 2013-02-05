package ambient.api.user

import com.mongodb.casbah.Imports._
import ambient.api.location.Location

class UserMapper {

  def map(doc: DBObject): User = {
    val id = Some(doc.as[ObjectId]("_id").toString)
    val first = doc.as[String]("first")
    val last = doc.as[String]("last")
    val fbid = doc.getAs[String]("fbid")
    val location = reverse(doc.getAs[Seq[Double]]("location"))

    User(id, fbid, first, last, location)
  }

  private def reverse(location: Option[Seq[Double]]): Option[Location] = location match {
    case  Some(Seq(long, lat)) => Some(Location(lat, long))
    case _ => None
  }
}




