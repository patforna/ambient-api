package ambient.api.search

import org.scalatest.{OneInstancePerTest, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.casbah.MongoDB
import ambient.api.location.Location
import ambient.api.user.{UserBuilder, User}
import org.scalatest.mock.MockitoSugar
import org.mockito.Matchers._

import org.mockito.Mockito._
import com.mongodb.{CommandResult, DBObject}

class SearchServiceTest extends FunSpec with ShouldMatchers with OneInstancePerTest with MockitoSugar {

  private val NEARBY_USERS = List(Nearby(UserBuilder().build, 42))
  private val RESULT = mock[CommandResult]

  private val db = mock[MongoDB]
  when(db.command(any[DBObject])).thenReturn(RESULT)

  private val mapper = mock[NearbyMapper]
  when(mapper.map(RESULT)).thenReturn(NEARBY_USERS)

  it("should find nearby users") {
    val service = new SearchService(db, mapper)
    service.findNearby(Location(10, 20)) should be(NEARBY_USERS)
  }
}
