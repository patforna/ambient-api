package ambient.api.search

import org.scalatra._
import org.scalatra.json._
import org.json4s.DefaultFormats

class SearchController extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/nearby") {
    import org.json4s.mongo.JObjectParser.serialize

    val users = findNearby map { u => Map("user" -> serialize(u), "distance" -> 70) }
    Map("nearby" -> users)
  }

  import com.mongodb.casbah.Imports._
  private def findNearby: List[DBObject] = {
    val collection = MongoClient("localhost")("ambient")("users")
    val fields = MongoDBObject("_id" -> 0)
    collection.find(MongoDBObject.empty, fields).toList
  }

}
