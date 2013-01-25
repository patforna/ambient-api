package ambient.api.functional

import org.scalatra.test.scalatest.ScalatraSpec
import org.scalatra.servlet.ScalatraListener

trait FunctionalSpec extends ScalatraSpec with SpecSugar {
  override def start() {
    servletContextHandler.addEventListener(new ScalatraListener)
    super.start
  }
}
