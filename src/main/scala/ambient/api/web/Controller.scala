package ambient.api.web

import org.scalatra.ScalatraServlet
import java.util.NoSuchElementException

trait Controller extends ScalatraServlet {

  error {
    case _: IllegalArgumentException | _: NoSuchElementException => halt(400)
  }

}
