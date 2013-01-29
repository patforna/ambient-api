package ambient.api.functional

import org.json4s.JsonAST.JValue
import org.json4s.mongo.JObjectParser
import org.json4s.DefaultFormats

object JsonHelpers {
  def serialize(obj: Any):JValue = JObjectParser.serialize(obj)(DefaultFormats)
}
