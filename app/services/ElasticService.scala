package services

import javax.inject.{Inject, Singleton}
import model.Doc
import play.api.libs.json.Json
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ElasticService @Inject()(val ws: WSClient)(implicit val ec: ExecutionContext) {

  def send(enrichedWithId: Doc): Future[WSResponse] = {
    ws.url("http://localhost:9200/docs/_doc").post(Json.toJson(dto.Doc.from(enrichedWithId)))
  }
}
