package ambient.api.search

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import org.json4s.mongo.JObjectParser

import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._
import ambient.api.user.User

class NearbyMapperTest extends FunSpec with ShouldMatchers {

  private val JSON =
    """
      |{
      |  "serverUsed" : "localhost/127.0.0.1:27017",
      |  "ns" : "ambient.users",
      |  "near" : "0110111010111010111011001000011000001100010110010100",
      |  "results" : [ {
      |    "dis" : 2550.9179842894273,
      |    "obj" : {
      |      "_id" : "51006bda270d87f45f479a04",
      |      "name" : "Jae Lee",
      |      "job" : "Developer at Forward",
      |      "location" : [ -0.136677, 51.537731 ]
      |    }
      |  }, {
      |    "dis" : 2551.5466370459762,
      |    "obj" : {
      |      "_id" : "51006be4270d87f45f479a05",
      |      "name" : "Marc Hofer",
      |      "job" : "Developer at ThoughtWorks",
      |      "location" : [ -0.099392, 51.531974 ]
      |    }
      |  } ]
      |}
    """.stripMargin

  private val JAE = Nearby(User("Jae Lee"), 2550)
  private val HOFF = Nearby(User("Marc Hofer"), 2551)

  private val mapper = new NearbyMapper

  describe("map") {
    it("should convert geoNear results to Nearby objects") {


      mapper.map(dbObjectFrom(JSON)) should be(List(JAE, HOFF))
    }

    it("should gracefully handle empty results") {
      mapper.map(dbObjectFrom("{}")) should have size 0
    }
  }

  private def dbObjectFrom(json: String) = JObjectParser.parse(parse(json))(DefaultFormats)
}
