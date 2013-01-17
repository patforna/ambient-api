package ambient.api.skeleton

import org.scalatra._
import org.scalatra.json._
import org.json4s.{DefaultFormats, Formats}

class SkeletonController extends ScalatraServlet with JacksonJsonSupport {

  implicit protected val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/") {
    List(Skeleton("Jack Skellington", "Pumpkin King"), Skeleton("Oogie Boogie", "Bogeyman"), Skeleton("Dr. Finkelstein", "Mad Scientist"))
  }

}
