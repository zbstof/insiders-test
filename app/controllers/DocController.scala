package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, ControllerComponents}
import services.Counter

/**
  * This controller demonstrates how to use dependency injection to
  * bind a component into a controller class. The class creates an
  * `Action` that shows an incrementing count to users. The [[Counter]]
  * object is injected by the Guice dependency injection system.
  */
@Singleton
class DocController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {

  /**
    * Create an action that responds with the [[Counter]]'s current
    * count. The result is plain text. This `Action` is mapped to
    * `GET /count` requests by an entry in the `routes` config file.
    */
  def create = Action {
    ws.url("localhost:9200").post()
    Created("ok, cool")
  }

  def get(id: Long) = Action {
    Ok("here you go")
  }
}
