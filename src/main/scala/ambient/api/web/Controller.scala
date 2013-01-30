package ambient.api.web

import org.scalatra.ScalatraServlet
import java.util.NoSuchElementException
import ambient.api.platform.Logger

trait Controller extends ScalatraServlet with Logger {

  error {
    case _: IllegalArgumentException | _: NoSuchElementException => halt(400)
    case e: Throwable => log.error("Unhandled exception: " + e)
  }

}
