package ambient.api.config

import ambient.api.search.{NearbyMapper, SearchService, SearchController}
import com.mongodb.casbah.MongoClient
import ambient.api.skeleton.SkeletonController

object Dependencies {

  lazy val db = MongoClient("localhost")("ambient")

  lazy val nearbyMapper = new NearbyMapper

  lazy val searchService = new SearchService(db, nearbyMapper)

  lazy val searchController = new SearchController(searchService)

  lazy val skeletonController = new SkeletonController
}
