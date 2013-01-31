package ambient.api.web

import org.scalatra.json.JacksonJsonSupport
import org.json4s.DefaultFormats

trait JsonSupport extends JacksonJsonSupport {

  protected implicit val jsonFormats = DefaultFormats

  before() {
    contentType = formats("json")
  }
}
