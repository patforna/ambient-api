package ambient.api.web

import org.scalatra.json.JacksonJsonSupport
import org.json4s.DefaultFormats
import ambient.api.location.LocationSerializer

trait JsonSupport extends JacksonJsonSupport {

  protected implicit val jsonFormats = DefaultFormats + new LocationSerializer

  before() {
    contentType = formats("json")
  }
}
