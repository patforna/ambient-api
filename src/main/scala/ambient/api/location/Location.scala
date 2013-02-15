package ambient.api.location

object Location {

  private val SPACES = "\\s*"
  private val DOUBLE = "(-?\\+?\\d+(?:\\.\\d+)?)"
  private val VALUE = SPACES + DOUBLE + SPACES
	private val LOCATION_PATTERN = (VALUE + "," + VALUE).r

	def apply(input: String): Location = input match {
		case LOCATION_PATTERN(lat, long) => Location(lat.toDouble, long.toDouble)
		case _ => throw new IllegalArgumentException(s"'$input' does not appear to be a valid location")
	}

  def apply(input: Option[Seq[Double]]): Option[Location] = input match {
    case Some(x) => Some(Location(x, reverse = true))
    case _ => None
  }

  def apply(input: Seq[Double], reverse: Boolean = true): Location = input match {
    case (Seq(long, lat)) if reverse => Location(lat, long)
    case (Seq(lat, long)) if !reverse => Location(lat, long)
    case _ => throw new IllegalArgumentException(s"'$input' does not appear to contain exactly two elements")
  }
}

case class Location(latitude: Double, longitude: Double) {
  if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180)
    throw new IllegalArgumentException("Lat/Long must be in the range -90 to 90 and -180 to 180, respectively.")

  def reverse: (Double, Double) = (longitude, latitude)
}
