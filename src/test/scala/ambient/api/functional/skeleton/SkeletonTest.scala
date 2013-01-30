package ambient.api

import functional.FunctionalSpec
import ambient.api.functional.JsonHelpers._

class SkeletonTest extends FunctionalSpec {

  it("should find the skeletons") {
    get("/")(asJson)
    json(responseJson) should include(json("""{"name":"Oogie Boogie","job":"Bogeyman"}"""))
  }
}
