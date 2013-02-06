package ambient.api.location

import ambient.api.web.{PrettyJsonSupport, Controller}
import ambient.api.user.User
import ambient.api.config.Keys

class CheckinController(service: CheckinService) extends Controller with PrettyJsonSupport {

  post("/") {
    val user = User("Jae", "Lee") // FIXME
    val location = Location(params(Keys.Location))
    service.checkin(user, location)
  }

}
