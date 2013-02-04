package ambient.api.platform

class NotFoundException(message: String) extends RuntimeException(message) {
  def this() = this(null)
}
