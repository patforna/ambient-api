package ambient.api.integration.web

import org.scalatra.test.scalatest.ScalatraSpec
import ambient.api.web.Controller
import ambient.api.integration.HttpSupport
import ambient.api.platform.NotFoundException

class ControllerTest extends ScalatraSpec with HttpSupport {

  addServlet(classOf[TestController], "/test/*")

  describe("general controller behaviour") {

    it("should 400 if a IllegalArgumentException was thrown") {
      get("/test/iae")(statusCode) should be(400)
    }

    it("should 400 if a NoSuchElementException was thrown") {
      get("/test/nsee")(statusCode) should be(400)
    }

    it("should 404 if a NotFoundException was thrown") {
      get("/test/nfe")(statusCode) should be(404)
    }

    it("should not contain a response body if response was not OK") {
      get("/test/iae")(_.body) should be('empty)
      get("/test/nsee")(_.body) should be('empty)
      get("/test/nfe")(_.body) should be('empty)
    }

  }

}

class TestController extends Controller {
  get("/iae") { throw new IllegalArgumentException }
  get("/nsee") { throw new NoSuchElementException }
  get("/nfe") { throw new NotFoundException }
}

