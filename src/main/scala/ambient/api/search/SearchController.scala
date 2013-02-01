package ambient.api.search

import ambient.api.location.Location
import ambient.api.web.{PrettyJsonSupport, Controller}

class SearchController(service: SearchService) extends Controller with PrettyJsonSupport {

  get("/nearby") {
    val location = Location(params("location"))
    Map("nearby" -> service.findNearby(location))
  }

}
