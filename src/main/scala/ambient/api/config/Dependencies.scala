package ambient.api.config

import ambient.api.search.{NearbyMapper, SearchService, SearchController}
import ambient.api.skeleton.SkeletonController
import ambient.api.config.Properties._
import ambient.api.location.{CheckinService, CheckinController}

object Dependencies {

  lazy val db = {
    val MongoSetting(db) = MongoUrl
    db
  }

  lazy val nearbyMapper = new NearbyMapper

  lazy val searchService = new SearchService(db, nearbyMapper)

  lazy val checkinService = new CheckinService(db)

  lazy val searchController = new SearchController(searchService)

  lazy val checkinController = new CheckinController(checkinService)

  lazy val skeletonController = new SkeletonController
}


