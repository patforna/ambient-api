package ambient.api.web

import org.scalatra.json.JacksonJsonSupport
import ambient.api.config.JsonFormats

trait JsonSupport extends JacksonJsonSupport {

  protected implicit val jsonFormats = JsonFormats.default

  before() {
    contentType = formats("json")
  }
}
