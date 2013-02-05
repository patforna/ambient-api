package ambient.api.search

import ambient.api.user.UserMapper
import com.mongodb.casbah.Imports._

class NearbyMapper(userMapper: UserMapper) {

  def map(doc: DBObject): List[Nearby] = {

    // TODO pull out "CollectionMapper" #maybe
    val results = doc.getAsOrElse[MongoDBList]("results", new MongoDBList) map { _.asInstanceOf[DBObject] }

    (results map {
      x =>
        val distance = x.as[Double]("dis").toInt
        val user = userMapper.map(x.as[DBObject]("obj"))
        Nearby(user, distance)
    }).toList
  }
}




