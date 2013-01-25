package ambient.api

import functional.FunctionalSpec
import org.scalatra.test.scalatest.ScalatraSpec
import skeleton.SkeletonController

class SkeletonTest extends FunctionalSpec {

  it("should find the skeletons") {
    get("/") {
      status should equal (200)
      response.mediaType should equal (Some("application/json"))
      response.body should include ("""{"name":"Oogie Boogie","job":"Bogeyman"}""")
    }
  }
}
