package ambient.api.web

import org.scalatra.{InternalServerError, BadRequest, ScalatraServlet}
import java.util.NoSuchElementException
import ambient.api.platform.Logger

trait Controller extends ScalatraServlet with Logger {

  error {
    case _: IllegalArgumentException | _: NoSuchElementException => BadRequest()
    case e => {
      log.error("Unhandled exception: " + e)
      InternalServerError()
    }
  }

}
