package com.pkxd.utils.errors

import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.features.toLogString
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import org.slf4j.Logger

fun StatusPages.Configuration.exceptions(logger: Logger) {
  exception<IllegalArgumentException> {
    logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)
    call.respond(HttpStatusCode.BadRequest, ErrorResponse(HttpStatusCode.BadRequest, "Invalid Format"))
  }
  exception<Throwable> {
    logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)
    call.respond(
      HttpStatusCode.InternalServerError,
      ErrorResponse(HttpStatusCode.InternalServerError, it.message ?: "")
    )
  }
}