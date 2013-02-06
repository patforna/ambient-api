package ambient.api.functional

import org.scalatra.test.scalatest.ScalatraSpec
import org.scalatra.servlet.ScalatraListener
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods._
import scala.Some
import org.scalatra.test.ClientResponse
import org.scalatest.BeforeAndAfterEach
import ambient.api.integration.HttpSupport

trait FunctionalSpec extends ScalatraSpec with BeforeAndAfterEach with SpecSugar with HttpSupport {

  var responseJson: JValue = _

  override def start() {
    servletContextHandler.addEventListener(new ScalatraListener)
    super.start()
  }

  def asJson(response: ClientResponse) {
    response.status should be(200)
    response.mediaType should be(Some("application/json"))
    responseJson = parse(response.body)
  }
}


