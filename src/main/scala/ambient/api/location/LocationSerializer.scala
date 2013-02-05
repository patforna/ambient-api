package ambient.api.location

import org.json4s.CustomSerializer
import org.json4s.JsonAST.{JArray, JDouble, JField, JObject}

class LocationSerializer extends CustomSerializer[Location](format => (
  {
    case JObject(JField("latitude", JDouble(lat)) :: JField("longitude", JDouble(long)) :: Nil) => new Location(lat, long)
  },
  {
    case x: Location =>
      JArray(List(JDouble(x.latitude), JDouble(x.longitude)))
  }
  ))
