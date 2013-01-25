package ambient.api.search

import org.scalatra._
import org.scalatra.json._
import org.json4s.DefaultFormats
import java.io.Writer
import org.json4s.JsonAST.JValue
import ambient.api.platform.Logger

object Location {
	
	private val LocationPattern = """(\d+),(\d+)""".r
	
	def isValid(location: String): Boolean = LocationPattern.findFirstMatchIn(location).isDefined
	
	def parse(s: String): Location =  s match {
		case LocationPattern(lat, long) => Location(lat.toDouble, long.toDouble)
		case _ => throw new IllegalArgumentException("'%s' does not appear to be a valid location" format s)
	}
}

case class Location(lat: Double, long: Double)

class SearchController extends ScalatraServlet with JacksonJsonSupport with Logger {

  private val service = new SearchService
  // TODO can this be private?
  protected implicit val jsonFormats = DefaultFormats

  override protected def writeJson(json: JValue, writer: Writer) {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, json)
  }

  before() {
    contentType = formats("json")
  }

  get("/nearby") {
	//params.get("location") match { case Some(LocationPattern(lat,long)) => (lat, long); case _ => println("boo")  }
	val location = params.get("location") match { case Some(x) if Location.isValid(x) => Location.parse(x); case _ => halt(400) }
	log.error("Location: " + location)	
	
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
