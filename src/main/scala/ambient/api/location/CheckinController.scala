package ambient.api.location

import ambient.api.web.{PrettyJsonSupport, Controller}
import ambient.api.config.Keys
import ambient.api.config.Keys._

class CheckinController(service: CheckinService) extends Controller with PrettyJsonSupport {

  post("/") {
    service.checkin(params(UserId), Location(params(Keys.Location)))
  }

}
