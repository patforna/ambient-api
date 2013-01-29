package ambient.api.search

import ambient.api.location.Location
import ambient.api.web.{Controller, JsonSupport}

class SearchController(service: SearchService) extends Controller with JsonSupport {

  get("/nearby") {
    val location = Location(params("location"))
    Map("nearby" -> service.findNearby(location))
  }

}
