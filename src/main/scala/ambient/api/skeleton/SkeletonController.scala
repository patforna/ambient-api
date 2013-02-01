package ambient.api.skeleton

import ambient.api.web.{PrettyJsonSupport, Controller}

class SkeletonController extends Controller with PrettyJsonSupport {

  get("/") {
    List(Skeleton("Jack Skellington", "Pumpkin King"), Skeleton("Oogie Boogie", "Bogeyman"), Skeleton("Dr. Finkelstein", "Mad Scientist"))
  }

}
