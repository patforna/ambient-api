package ambient.api.user

import ambient.api.web.{PrettyJsonSupport, Controller}

class UserController(service: UserService) extends Controller with PrettyJsonSupport {

  get("/search") {
    val fb = params("fb")
    Map("user" -> service.search(fb))
  }

}
