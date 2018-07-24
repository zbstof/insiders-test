package dto

import play.api.libs.json.{Json, Reads, Writes}

case class Doc(id: Option[String], name: String)

object Doc {
  implicit val writes: Writes[Doc] = Json.writes[Doc]

  implicit val reads: Reads[Doc] = Json.reads[Doc]

  def from(modelDoc: model.Doc): dto.Doc =
    Doc(Some(modelDoc.id.stringify), modelDoc.name)
}
