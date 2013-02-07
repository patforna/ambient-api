package ambient.api.config

import ambient.api.search.{NearbyMapper, SearchService, SearchController}
import ambient.api.config.Properties._
import ambient.api.location.{CheckinService, CheckinController}
import ambient.api.user.{UserMapper, UserController, UserService}

object Dependencies {

  lazy val db = {
    val MongoSetting(db) = MongoUrl
    db
  }

  lazy val userMapper = new UserMapper

  lazy val nearbyMapper = new NearbyMapper(userMapper)

  lazy val searchService = new SearchService(db, nearbyMapper)

  lazy val checkinService = new CheckinService(db)

  lazy val userService = new UserService(db, userMapper)

  lazy val searchController = new SearchController(searchService)

  lazy val checkinController = new CheckinController(checkinService)

  lazy val userController = new UserController(userService)
}


