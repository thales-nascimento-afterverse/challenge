package com.pkxd.utils.errors

import io.ktor.application.call
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import kotlinx.serialization.SerializationException
import org.slf4j.Logger

fun StatusPages.Configuration.exceptions(logger: Logger) {
  exception<SerializationException> {
    logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)
    call.respond(
      HttpStatusCode.BadRequest,
      ErrorResponse(HttpStatusCode.BadRequest, "Required fields are missing")
    )
  }

  exception<Throwable> {
    logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)
    call.respond(
      HttpStatusCode.InternalServerError,
      ErrorResponse(HttpStatusCode.InternalServerError, it.message ?: "")
    )
  }
}