package ambient.api.functional

trait SpecSugar {

  protected def given[T](block: => T):T = block
  protected def when[T](block: => T):T = block
  protected def `then`[T](block: => T):T = block
  protected def and[T](block: => T):T = block

}
