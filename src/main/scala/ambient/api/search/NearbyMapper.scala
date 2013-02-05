package ambient.api.search

import ambient.api.user.User

import com.mongodb.casbah.Imports._


class NearbyMapper {

  def map(result: DBObject): List[Nearby] = {

    val results = result.getAsOrElse[MongoDBList]("results", new MongoDBList) map { _.asInstanceOf[DBObject] }

    (results map {
      x =>
        val distance = x.as[Double]("dis").toInt
        val obj = x.as[DBObject]("obj")
        val first = obj.as[String]("first")
        val last = obj.as[String]("last")
        val fbid = obj.getAs[String]("fbid")
        Nearby(User(None, fbid, first, last), distance)
    }).toList
  }
}




