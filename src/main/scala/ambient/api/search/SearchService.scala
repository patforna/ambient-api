package ambient.api.search

import org.json4s._
import ambient.api.user.User
import ambient.api.location.Location

import org.json4s.mongo.JObjectParser
import com.mongodb.casbah.Imports._

import org.json4s.DefaultFormats

class SearchService {

  private val EarthRadiusInMeter = 6378137

  private val db = MongoClient("localhost")("ambient")

  def findNearby(location: Location): List[Nearby] = {
    val command: DBObject = MongoDBObject("geoNear" -> "users", "near" ->(location.longitude, location.latitude), "spherical" -> true, "distanceMultiplier" -> EarthRadiusInMeter)
    val result = db.command(command)

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




