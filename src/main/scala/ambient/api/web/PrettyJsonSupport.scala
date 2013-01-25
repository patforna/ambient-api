package ambient.api.web

import org.scalatra.json.JacksonJsonSupport
import org.json4s.JsonAST.JValue
import java.io.Writer

trait PrettyJsonSupport extends JacksonJsonSupport {
  override protected def writeJson(json: JValue, writer: Writer) {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, json)
  }
}
