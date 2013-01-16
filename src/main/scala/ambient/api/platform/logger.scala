package ambient.api.platform

import org.slf4j.{LoggerFactory, Logger => Slf4jLogger}

trait Logger {
  val log = new LoggerWrapper(LoggerFactory.getLogger(getClass))
}

sealed class LoggerWrapper(delegate: Slf4jLogger) {

  def trace(message: => String) = if (delegate.isTraceEnabled) delegate.trace(message)

  def debug(message: => String) = if (delegate.isDebugEnabled) delegate.debug(message)

  def debug(message: => String, e:Throwable) = if (delegate.isDebugEnabled) delegate.debug(message, e)

  def info(message: => String) = if (delegate.isInfoEnabled) delegate.info(message)

  def error(message: => String) = if (delegate.isErrorEnabled) delegate.error(message)

}