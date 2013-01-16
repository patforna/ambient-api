package ambient.api.skeleton

import org.scalatra._
//import org.json4s.{DefaultFormats, Formats}
//import org.scalatra.json._

class SkeletonController extends ScalatraServlet { //with JacksonJsonSupport {

//  protected implicit val jsonFormats: Formats = DefaultFormats
//
//  before() {
//    contentType = formats("json")
//  }

  get("/") {
    List(Skeleton("Jack Skellington", "Pumpkin King"), Skeleton("Oogie Boogie", "Bogeyman"), Skeleton("Dr. Finkelstein", "Mad Scientist"))
  }

}
