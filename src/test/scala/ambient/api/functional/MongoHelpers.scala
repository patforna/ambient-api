package ambient.api.functional

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.TypeImports.DBObject
import ambient.api.config.Keys._
import com.mongodb.casbah.commons.MongoDBObject

import scala.language.implicitConversions
import ambient.api.user.User
import ambient.api.config.Keys
import ambient.api.location.Location

object MongoHelpers {

  def clearCollection()(implicit collection: MongoCollection) {
    collection.remove(MongoDBObject.empty)
  }

  def insert(user: User)(implicit collection: MongoCollection): ObjectId = {
    insert(mongoify(user.asMap))
  }

  def insert(keyValues: (String, Any)*)(implicit collection: MongoCollection): ObjectId = {
    insert(keyValues.toMap)
  }

  def insert(document: DBObject)(implicit collection: MongoCollection): ObjectId = {
    collection.insert(document)
    document.as[ObjectId](Id)
  }

  private def mongoify(map: Map[String, Any]): Map[String, Any] = map map {
    case (key, value) =>
      if (key == Keys.Location) value match { case Some(x) => (key, Some(x.asInstanceOf[Location].reverse)); case _ => (key, None) }
      else (key, value)
  }
}
