package ambient.api.search

import ambient.api.user.User

case class Nearby(user: User, distance: Int)
