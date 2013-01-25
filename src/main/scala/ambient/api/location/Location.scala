package ambient.api.location

object Location {

  private val SPACES = "\\s*"
  private val DOUBLE = "(-?\\d+(?:\\.\\d+)?)"
  private val VALUE = SPACES + DOUBLE + SPACES
	private val LOCATION_PATTERN = (VALUE + "," + VALUE).r

	def isLocation(location: String): Boolean = LOCATION_PATTERN.findFirstMatchIn(location).isDefined

	def apply(value: String): Location = value match {
		case LOCATION_PATTERN(lat, long) => Location(lat.toDouble, long.toDouble)
		case _ => throw new IllegalArgumentException(s"'${value}' does not appear to be a valid location")
	}
}

case class Location(latitude: Double, longitude: Double) {
  if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180)
    throw new IllegalArgumentException("Lat/Long must be in the range -90 to 90 and -180 to 180, respectively.")
}
