package model

import play.api.libs.json.{Format, Json, Reads, Writes}

case class Doc(name: String)

object Doc {
  implicit val format: Format[Doc] = Json.format[Doc]

  implicit val writes: Writes[Doc] = Json.writes[Doc]

  implicit val reads: Reads[Doc] = Json.reads[Doc]
}