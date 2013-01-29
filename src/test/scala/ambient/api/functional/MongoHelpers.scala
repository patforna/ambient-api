package ambient.api.functional

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.TypeImports.DBObject
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonAST.JValue
import ambient.api.functional.JsonHelpers._
import org.json4s.mongo.JObjectParser
import org.json4s.DefaultFormats

object MongoHelpers {

  // returns _id of inserted document
  def insert(string: String)(implicit collection: MongoCollection): AnyRef = {
    val doc = stringToDBObject(string)
    collection.insert(doc)
    doc("_id")
  }

  def findOneById(id: AnyRef)(implicit collection: MongoCollection): JValue = serialize(collection.findOneByID(id).get)

  private def stringToDBObject(string: String): DBObject = {
    val json = parse(string)
    JObjectParser.parse(json)(DefaultFormats)
  }
}
