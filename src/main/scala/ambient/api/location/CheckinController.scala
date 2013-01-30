package ambient.api.location

import ambient.api.web.{Controller, JsonSupport}
import ambient.api.user.User

class CheckinController(service: CheckinService) extends Controller with JsonSupport {

  post("/") {
    val user = User("Jae Lee")//User(params("user"))
    val location = Location(params("location"))
    service.checkin(user, location)
  }

}
