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
    val fbid = None // TODO should fbid be even in here?
    val location = Location(doc.getAs[Seq[Double]](Keys.Location))

    User(id, first, last, fbid, location)
  }
}




