package ambient.api.integration

import org.scalatra.test.{ClientResponse, Client}

trait HttpSupport extends Client {

  def get[T](request: String)(implicit block: (ClientResponse => T)): T = {
    block(super.get(request)(super.response))
  }

  def post[T](request: String)(implicit block: (ClientResponse => T)): T = {
    block(super.post(request)(super.response))
  }

  def statusCode(response: ClientResponse): Int = response.status

}
