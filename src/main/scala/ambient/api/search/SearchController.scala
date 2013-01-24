package ambient.api.search

import org.scalatra._
import org.scalatra.json._
import org.json4s.DefaultFormats
import java.io.Writer
import org.json4s.JsonAST.JValue

class SearchController extends ScalatraServlet with JacksonJsonSupport {

  private val service = new SearchService
  protected implicit val jsonFormats = DefaultFormats

  override protected def writeJson(json: JValue, writer: Writer) {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, json)
  }

  before() {
    contentType = formats("json")
  }

  get("/nearby") {
    Map("nearby" -> service.findNearby)
  }


  //import org.json4s.mongo.JObjectParser.serialize
  //val users = service.findNearby map { u => Map("user" -> serialize(u), "distance" -> 70) }
  //  import com.mongodb.casbah.Imports._
  //  private def findSkeletons: List[DBObject] = {
  //    val collection = MongoClient("localhost")("ambient")("users")
  //    val fields = MongoDBObject("_id" -> 0)
  //    collection.find(MongoDBObject.empty, fields).toList
  //  }
  //
  //

}
