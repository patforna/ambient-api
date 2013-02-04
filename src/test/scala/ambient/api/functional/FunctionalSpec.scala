package ambient.api.functional

import org.scalatra.test.scalatest.ScalatraSpec
import org.scalatra.servlet.ScalatraListener
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods._
import scala.Some
import org.scalatra.test.ClientResponse
import org.scalatest.BeforeAndAfterEach
import org.json4s.DefaultFormats

trait FunctionalSpec extends ScalatraSpec with BeforeAndAfterEach with SpecSugar {

  implicit val jsonFormats = DefaultFormats

  var responseJson: JValue = _

  override def start() {
    servletContextHandler.addEventListener(new ScalatraListener)
    super.start
  }

  def get[T](request: String)(implicit block: (ClientResponse => T)): T = {
    block(super.get(request)(super.response))
  }

  def post[T](request: String)(implicit block: (ClientResponse => T)): T = {
    block(super.post(request)(super.response))
  }

  def statusCode(response: ClientResponse): Int = response.status

  def asJson(response: ClientResponse) {
    response.status should be(200)
    response.mediaType should be(Some("application/json"))
    responseJson = parse(response.body)
  }

}
