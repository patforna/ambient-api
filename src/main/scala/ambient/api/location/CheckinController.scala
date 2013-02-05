package ambient.api.location

import ambient.api.web.{PrettyJsonSupport, Controller}
import ambient.api.user.User

class CheckinController(service: CheckinService) extends Controller with PrettyJsonSupport {

  post("/") {
    val user = User("Jae", "Lee") // FIXME
    val location = Location(params("location"))
    service.checkin(user, location)
  }

}
