package ambient.api.functional

import org.scalatra.test.scalatest.ScalatraSpec
import org.scalatra.servlet.ScalatraListener
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods._
import scala.Some
import org.scalatra.test.ClientResponse

trait FunctionalSpec extends ScalatraSpec with SpecSugar {

  var responseJson: JValue = _

  var _response: ClientResponse = _
  override def response: ClientResponse = _response

  override def start() {
    servletContextHandler.addEventListener(new ScalatraListener)
    super.start
  }

  def get[T](request: String)(implicit block: (ClientResponse => T)): T = {
    _response = super.get(request)(super.response)
    block(_response)
  }

  def post[T](request: String)(implicit block: (ClientResponse => T)): T = {
    _response = super.post(request)(super.response)
    block(_response)
  }

  def statusCode(response: ClientResponse) = response.status

  def asJson(response: ClientResponse) = {
    response.status should be(200)
    response.mediaType should be(Some("application/json"))
    responseJson = parse(response.body)
  }

}
