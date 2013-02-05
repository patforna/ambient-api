package ambient.api.web

import org.scalatra._
import java.util.NoSuchElementException
import ambient.api.platform.{NotFoundException, Logger}

trait Controller extends ScalatraServlet with Logger {

  error {
    case e: IllegalArgumentException => logAsWarning(e, 400); BadRequest()
    case e: NoSuchElementException => logAsWarning(e, 400); BadRequest()
    case e: NotFoundException => logAsWarning(e, 404); NotFound(body = "")
    case e => logAsError(e, 500); InternalServerError()
  }

  private def logAsWarning(e: Throwable, statusCode: Int) {
    log.warn(s"Caught exception: ${e.getMessage}. Returning $statusCode")
  }

  private def logAsError(e: Throwable, statusCode: Int) {
    log.warn(s"Unhandled exception: ${e.getMessage}. Returning $statusCode", e)
  }

}
