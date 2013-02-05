package ambient.api.user

import ambient.api.web.{PrettyJsonSupport, Controller}

class UserController(service: UserService) extends Controller with PrettyJsonSupport {

  post("/") {
    Map("user" -> service.create(User.from(params)))
  }

  get("/search") {
    val fbid = params("fbid")
    Map("user" -> service.search(fbid))
  }

}
