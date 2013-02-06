package ambient.api.search

import ambient.api.location.Location

import com.mongodb.casbah.Imports._

class SearchService(db: MongoDB, mapper: NearbyMapper) {

  private val EARTH_RADIUS_IN_METERS = 6378137

  def findNearby(location: Location): List[Nearby] = {
    val cmd = Map("geoNear" -> "users", "near" -> (location.longitude, location.latitude), "spherical" -> true, "distanceMultiplier" -> EARTH_RADIUS_IN_METERS)
    val result = db.command(cmd)
    result.throwOnError // TODO unit test #maybe
    mapper.map(result)
  }
}




