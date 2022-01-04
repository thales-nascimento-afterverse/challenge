package com.pkxd.main.errors

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import kotlinx.serialization.SerializationException

fun StatusPages.Configuration.exceptions() {
    exception<SerializationException> {
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse(HttpStatusCode.BadRequest, "Required fields are missing")
        )
    }

    exception<Throwable> {
        println("${it.printStackTrace()}")
        call.respond(
            HttpStatusCode.InternalServerError,
            ErrorResponse(HttpStatusCode.InternalServerError, it.message ?: "")
        )
    }
}