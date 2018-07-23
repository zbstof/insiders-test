package controllers

import javax.inject.{Inject, Singleton}
import model.Doc
import play.api.Logger
import play.api.libs.json.Json
import play.api.libs.ws.JsonBodyWritables.writeableOf_JsValue
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.ExecutionContext

@Singleton
class DocController @Inject()(val cc: ControllerComponents,
                              implicit val ec: ExecutionContext,
                              ws: WSClient,
                              val reactiveMongoApi: ReactiveMongoApi) extends AbstractController(cc) {

  def create = Action { implicit request: Request[AnyContent] =>
    val jsonBody = request.body.asJson.get
    Logger.info("Request is: " + jsonBody)
    val doc: Doc = Json.fromJson[Doc](jsonBody).get
    ws.url("http://localhost:9200/simpledoc/_doc").post(Json.toJson(doc))
      .foreach(res => Logger.info("Response: " + res.body))
    Created
  }

  def get(id: Long) = Action {
    Ok
  }
}
