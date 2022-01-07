package com.pkxd

import com.fasterxml.jackson.databind.SerializationFeature
import com.pkxd.main.routes.productRoutes
import com.pkxd.main.routes.profileRoutes
import com.pkxd.utils.errors.exceptions
import com.pkxd.utils.logging.logger
import io.ktor.application.*
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.routing.routing

val logger = logger("com.pkxd.main")

fun main(args: Array<String>): Unit =
  io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
  install(CORS) {
    method(HttpMethod.Options)
    method(HttpMethod.Put)
    method(HttpMethod.Delete)
    method(HttpMethod.Patch)
    header(HttpHeaders.Authorization)
    header("MyCustomHeader")
    anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
  }

  install(ContentNegotiation) {
    jackson {
      enable(SerializationFeature.INDENT_OUTPUT)
    }
  }

  install(StatusPages) {
    exceptions(logger)
  }

  routing {
    profileRoutes()
    productRoutes()
  }
}
