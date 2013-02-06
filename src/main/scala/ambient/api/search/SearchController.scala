package ambient.api.search

import ambient.api.location.Location
import ambient.api.web.{PrettyJsonSupport, Controller}
import ambient.api.config.Keys

class SearchController(service: SearchService) extends Controller with PrettyJsonSupport {

  get("/nearby") {
    val location = Location(params(Keys.Location))
    Map("nearby" -> service.findNearby(location))
  }

}
