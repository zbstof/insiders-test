package controllers

import dao.DocDao
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json.{JsPath, Json, JsonValidationError}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import services.ElasticService

import scala.concurrent.ExecutionContext

@Singleton
class DocController @Inject()(val cc: ControllerComponents,
                              val dao: DocDao,
                              val elastic: ElasticService)
                             (implicit val ec: ExecutionContext) extends AbstractController(cc) {

  def create = Action { implicit request: Request[AnyContent] =>
    val dtoDoc: dto.Doc = Json.fromJson[dto.Doc](request.body.asJson.get).asEither match {
      case Right(value) => value
      case Left(errs: Seq[(JsPath, Seq[JsonValidationError])]) =>
        throw new IllegalArgumentException("Bad request: " + errs.last)}

    val enrichedWithId: model.Doc = model.Doc.from(dtoDoc)
    Logger.info("Enriched: " + enrichedWithId)

    dao.create(enrichedWithId)
      .flatMap(_ => elastic.send(enrichedWithId))
    Created(Json.toJson(dto.Doc.from(enrichedWithId)))
  }


  def get(id: String): Action[AnyContent] = Action.async {
    dao.read(id)
      .map((maybeRes: Option[model.Doc]) => {
        maybeRes match {
          case Some(res) => {
            Logger.info("Mongo: " + res)
            Ok(Json.toJson(dto.Doc.from(res)))
          }
          case None => throw new IllegalArgumentException("Not found: " + id)
        }
      })
  }

}
