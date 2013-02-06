package ambient.api.functional

import org.json4s.JsonAST.JValue
import org.json4s.mongo.JObjectParser
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write
import ambient.api.config.JsonFormats

object JsonHelpers {

  implicit val jsonFormats = JsonFormats.default

  def json(v: JValue): String = compact(render(v))

  def json(s: String): String = json(parse(s.stripMargin))

  def serialize(anything: AnyRef): String = write(anything)

  @deprecated
  def toJValue(obj: Any): JValue = JObjectParser.serialize(obj)(DefaultFormats) // FIXME get rid

}
