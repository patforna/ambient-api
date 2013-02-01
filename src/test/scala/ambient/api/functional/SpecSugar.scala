package ambient.api.functional

trait SpecSugar {

  private def runner(func: => Unit) = func
  protected val given, when, `then`, and = runner(_)

}
