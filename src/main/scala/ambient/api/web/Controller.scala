package ambient.api.web

import org.scalatra._
import java.util.NoSuchElementException
import ambient.api.platform.{NotFoundException, Logger}

trait Controller extends ScalatraServlet with Logger {

  error {
    case _: IllegalArgumentException | _: NoSuchElementException => BadRequest()
    case _: NotFoundException => NotFound(body = "")
    case e => {
      log.error("Unhandled exception", e)
      InternalServerError()
    }
  }

}
