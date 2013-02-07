package ambient.api.web

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener
import ambient.api.platform.Logger

class HttpServer(port: Int) extends Logger {

  private val WebappDir = "./webapp"

  private val server = new Server(port)
  server.setHandler(handler)

  def startAndWait() {
    start()
    server.join()
  }

  private def start() {
    log.info("Starting Web Server...")
    server.start()
    log.info("Web Server started. Waiting for incoming requests...")
  }

  private def handler = {
    val context = new WebAppContext()
    context.setContextPath("/")
    context.setResourceBase(WebappDir)
    context.addEventListener(new ScalatraListener)
    context
  }
}
