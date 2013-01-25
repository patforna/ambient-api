package ambient.api.functional.search

import org.scalatra.test.scalatest.ScalatraSpec
import ambient.api.search.SearchController

class SearchTest extends ScalatraSpec {

  addServlet(classOf[SearchController], "/search/*")

  it("should find nearby users") {
    import org.json4s.jackson.JsonMethods._
    // given there are some users in the system
    // TODO

    // when I look for users near a location
//    get("/search/nearby?location=51.515874,-0.125613") {
    get("/search/nearby?location=51,20") {	

      // then they should show up
      status should equal(200)
      response.mediaType should equal(Some("application/json"))
      compact(render(parse(response.body))) should be (compact(render(parse("""{
                                          "nearby": [
                                            {
                                              "user": {
                                                "name": "Jae Lee"
                                              },
                                              "distance": 70
                                            }
                                          ]
                                        }"""))))
    }
  }

  it("should bark if no location parameter is missing") {
    get("/search/nearby") { status should equal(400) }
    get("/search/nearby?location=") { status should equal(400) }
  }
}
