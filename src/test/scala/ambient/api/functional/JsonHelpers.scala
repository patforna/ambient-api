package ambient.api.functional

import org.json4s.JsonAST.JValue
import org.json4s.mongo.JObjectParser
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._

object JsonHelpers {

  def serialize(obj: Any):JValue = JObjectParser.serialize(obj)(DefaultFormats)

  def json(v: JValue): String = compact(render(v))

  def json(s: String): String = json(parse(s))

}
