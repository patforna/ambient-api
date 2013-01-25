package ambient.api.location

object Location {

	private val LocationPattern = """(\d+),(\d+)""".r

	def isLocation(location: String): Boolean = LocationPattern.findFirstMatchIn(location).isDefined

	def parse(s: String): Location =  s match {
		case LocationPattern(lat, long) => Location(lat.toDouble, long.toDouble)
		case _ => throw new IllegalArgumentException("'%s' does not appear to be a valid location" format s)
	}
}

case class Location(lat: Double, long: Double)
