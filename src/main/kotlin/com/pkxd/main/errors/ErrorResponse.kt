package com.pkxd.main.errors

import io.ktor.http.*
import io.ktor.http.content.*

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