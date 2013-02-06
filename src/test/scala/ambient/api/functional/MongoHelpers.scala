package ambient.api.functional

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.TypeImports.DBObject
import ambient.api.config.Keys._
import com.mongodb.casbah.commons.MongoDBObject

import scala.language.implicitConversions

object MongoHelpers {

  def clearCollection()(implicit collection: MongoCollection) {
    collection.remove(MongoDBObject.empty)
  }

  def insert(keyValues: (String, Any)*)(implicit collection: MongoCollection): ObjectId = {
    insert(keyValues.toMap)
  }

  def insert(document: DBObject)(implicit collection: MongoCollection): ObjectId = {
    collection.insert(document)
    document.as[ObjectId](Id)
  }
}
