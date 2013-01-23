package ambient.api

import org.scalatra.test.scalatest.ScalatraSpec
import skeleton.SkeletonController

class SkeletonTest extends ScalatraSpec {

  addServlet(classOf[SkeletonController], "/skeletons/*")

  it("should find the skeletons") {
    get("/skeletons/") {
      status should equal (200)
      response.mediaType should equal (Some("application/json"))
      response.body should include ("""{"name":"Oogie Boogie","job":"Bogeyman"}""")
    }
  }
}
