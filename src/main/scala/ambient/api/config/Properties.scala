package ambient.api.config

import scala.util.{Properties => ScalaProperties}

object Properties {
  lazy val MongoUrl = ScalaProperties.envOrNone("MONGOHQ_URL")
}
