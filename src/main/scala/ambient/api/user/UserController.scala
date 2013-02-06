package ambient.api.user

import ambient.api.web.{PrettyJsonSupport, Controller}
import ambient.api.config.Keys.Fbid

class UserController(service: UserService) extends Controller with PrettyJsonSupport {

  post("/") {
    Map("user" -> service.create(User.from(params)))
  }

  get("/search") {
    val fbid = params(Fbid)
    Map("user" -> service.search(fbid))
  }

}
