package ambient.api.search

import org.scalatra._
import org.scalatra.json._
import org.json4s.DefaultFormats
import java.io.Writer
import org.json4s.JsonAST.JValue
import ambient.api.location
import location.Location
import location.Location.isLocation
import ambient.api.web.PrettyJsonSupport

class SearchController extends ScalatraServlet with PrettyJsonSupport {

  private val service = new SearchService

  protected implicit val jsonFormats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/nearby") {
    val location = params.get("location") match {
      case Some(x) if isLocation(x) => Location.parse(x)
      case _ => halt(400)
    }

    Map("nearby" -> service.findNearby)
  }
}
