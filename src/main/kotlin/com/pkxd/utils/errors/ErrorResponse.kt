package com.pkxd.utils.errors

import io.ktor.http.HttpStatusCode
import io.ktor.http.content.OutgoingContent

data class ErrorResponse(
  override val status: HttpStatusCode,
  val description: String
) : OutgoingContent.ByteArrayContent() {

  override fun bytes(): ByteArray =
    """
      |{
      |  "status": "${status.description}",
      |  "description": "$description"
      |}
    """.trimMargin().toByteArray(Charsets.UTF_8)
}