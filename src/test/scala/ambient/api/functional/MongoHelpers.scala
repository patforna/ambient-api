package ambient.api.functional

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.TypeImports.DBObject
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonAST.JValue
import ambient.api.functional.JsonHelpers._
import org.json4s.mongo.JObjectParser
import org.json4s.DefaultFormats
import com.mongodb.casbah.commons.MongoDBObject

object MongoHelpers {

  implicit def stringToDBObject(string: String): DBObject = {
    val json = parse(string)
    JObjectParser.parse(json)(DefaultFormats)
  }

  def clearCollection()(implicit collection: MongoCollection) {
    collection.remove(MongoDBObject.empty)
  }

  // returns _id of inserted document
  def insert(document: DBObject)(implicit collection: MongoCollection): AnyRef = {
    collection.insert(document)
    document("_id")
  }


  // FIXME: no need to use json here
  def findOneById(id: AnyRef)(implicit collection: MongoCollection): JValue = serialize(collection.findOneByID(id).get)
}
