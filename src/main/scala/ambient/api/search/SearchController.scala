package ambient.api.search

import org.scalatra._
import org.json4s.DefaultFormats
import ambient.api.location.Location
import ambient.api.web.PrettyJsonSupport
import java.util.NoSuchElementException

class SearchController(service: SearchService) extends ScalatraServlet with PrettyJsonSupport {

  protected implicit val jsonFormats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/nearby") {
    val location = Location(params("location"))
    Map("nearby" -> service.findNearby(location))
  }

  error { case _: IllegalArgumentException | _: NoSuchElementException => halt(400) }
}
