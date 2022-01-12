package com.pkxd.utils.errors

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.features.toLogString
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import org.slf4j.Logger
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException

fun StatusPages.Configuration.exceptions(logger: Logger) {
  exception<IllegalArgumentException> {
    logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)
    call.respond(HttpStatusCode.BadRequest, ErrorResponse(HttpStatusCode.BadRequest, "Invalid Format"))
  }

  exception<MissingKotlinParameterException> {
    logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)
    call.respond(
      HttpStatusCode.BadRequest,
      ErrorResponse(HttpStatusCode.BadRequest, "Failed - Missing Params")
    )
  }

  exception<EntityNotFoundException> {
    logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)
    call.respond(
      HttpStatusCode.BadRequest,
      ErrorResponse(HttpStatusCode.BadRequest, it.message ?: "Entity not found")
    )
  }

  exception<EntityAlreadyExistsException> {
    logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)
    call.respond(
      HttpStatusCode.BadRequest,
      ErrorResponse(HttpStatusCode.BadRequest, it.message ?: "Entity already exists")
    )
  }

  exception<DynamoDbException> {
    logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)
    call.respond(
      HttpStatusCode.BadRequest,
      ErrorResponse(HttpStatusCode.BadRequest, "It was not possible to process the request")
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