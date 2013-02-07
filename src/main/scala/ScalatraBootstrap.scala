import org.scalatra._
import javax.servlet.ServletContext
import ambient.api.config.Dependencies._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(searchController, "/search/*")
    context.mount(checkinController, "/checkins")
    context.mount(userController, "/users")
  }
}
