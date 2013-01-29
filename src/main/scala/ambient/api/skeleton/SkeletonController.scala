package ambient.api.skeleton

import ambient.api.web.{Controller, JsonSupport}

class SkeletonController extends Controller with JsonSupport {

  get("/") {
    List(Skeleton("Jack Skellington", "Pumpkin King"), Skeleton("Oogie Boogie", "Bogeyman"), Skeleton("Dr. Finkelstein", "Mad Scientist"))
  }

}
