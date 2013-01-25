package ambient.api.search

import org.json4s._
import ambient.api.user.User
import ambient.api.location.Location

import org.json4s.mongo.JObjectParser
import com.mongodb.casbah.Imports._

import org.json4s.DefaultFormats

class NearbyMapper {

  def map(result: DBObject): List[Nearby] = {
    for {
      JArray(results) <- serialize(result) \ "results"
      JObject(result) <- results
      JField("dis", JDouble(distance)) <- result
      JField("obj", JObject(user)) <- result
      JField("name", JString(name)) <- user
      JField("job", JString(job)) <- user
    } yield Nearby(User(name), distance.toInt)
  }

  private def serialize(obj: Any) = JObjectParser.serialize(obj)(DefaultFormats)
}




