package ambient.api
import web.HttpServer

object Api {
  def main(args: Array[String]) {
    val port = if(System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080
    new HttpServer(port).startAndWait()
  }

}