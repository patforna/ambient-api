package ambient.api.search

import org.json4s._
//import org.json4s.jackson.JsonMethods._
import org.json4s.mongo.JObjectParser.serialize
import com.mongodb.casbah.Imports._

import org.json4s.DefaultFormats

class SearchService {

  private val EarthRadiusInMeter = 6378137
  private val db = MongoClient("localhost")("ambient")
  // TODO is this needed?
  private implicit val jsonFormats = DefaultFormats

  def findNearby: List[Nearby] = {
    val command: DBObject = MongoDBObject("geoNear" -> "users", "near" ->(-0.125613, 51.515874), "spherical" -> true, "distanceMultiplier" -> EarthRadiusInMeter)
    val result = db.command(command)
    val json = serialize(result)

    for {
      JArray(results) <- json \ "results"
      JObject(result) <- results
      JField("dis", JDouble(distance)) <- result
      JField("obj", JObject(user)) <- result
      JField("name", JString(name)) <- user
      JField("job", JString(job)) <- user
    } yield Nearby(User(name), distance.toInt)
  }
}

case class User(name: String)
case class Nearby(user: User, distance: Int)

