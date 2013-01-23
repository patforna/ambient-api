import ambient.api._
import org.scalatra._
import javax.servlet.ServletContext
import skeleton.SkeletonController
import search.SearchController

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new SearchController, "/search/*")
    context.mount(new SkeletonController, "/skeletons/*")
  }
}
