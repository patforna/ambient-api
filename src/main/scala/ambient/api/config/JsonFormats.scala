package ambient.api.config

import org.json4s.DefaultFormats
import ambient.api.location.LocationSerializer

object JsonFormats {
  val default = DefaultFormats + new LocationSerializer
}
