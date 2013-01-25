package ambient.api.config

import com.mongodb.casbah.Imports._

object MongoSetting {

  private val URL_FORMAT = """mongodb://(\w+):(\w+)@([\w|\.]+):(\d+)/(\w+)""".r
  private val DEFAULT_HOST = "localhost"
  private val DEFAULT_DB_NAME = "ambient"

  def unapply(url: Option[String]): Option[MongoDB] = {
    url match {
      case Some(URL_FORMAT(user, password, host, port, dbName)) =>
        val db = MongoConnection(host, port.toInt)(dbName)
        db.authenticate(user, password)
        Some(db)
      case None =>
        Some(MongoConnection(DEFAULT_HOST)(DEFAULT_DB_NAME))
    }
  }
}
