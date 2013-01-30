package ambient.api.functional

import org.scalatra.test.scalatest.ScalatraSpec
import org.scalatra.servlet.ScalatraListener
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods._
import scala.Some

trait FunctionalSpec extends ScalatraSpec with SpecSugar {

  protected var responseBody: JValue = _

  override def start() {
    servletContextHandler.addEventListener(new ScalatraListener)
    super.start
  }

  protected def getStatus(path: String) = get(path)(status)

  protected def getResponse(path: String) = {
    get(path) {
      status should be(200)
      response.mediaType should be(Some("application/json"))
      parse(body)
    }
  }
}
