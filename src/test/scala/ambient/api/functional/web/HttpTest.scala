package ambient.api.functional.web

import ambient.api.functional.Uri._
import ambient.api.functional.FunctionalSpec

class HttpTest extends FunctionalSpec {
  describe("general controller behaviour") {
    it("should not contain a response body if response was not OK") {
      get(SearchNearbyUri)(_.body) should be('empty)
    }
  }
}
