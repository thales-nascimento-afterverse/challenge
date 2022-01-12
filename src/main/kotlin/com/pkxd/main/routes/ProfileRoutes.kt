package com.pkxd.main.routes

import com.pkxd.dtos.profile.CreateProfileRequestDTO
import com.pkxd.main.config.Configuration.createProfileUseCase
import com.pkxd.main.config.Configuration.findProfileByIdUseCase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.routing.route
import io.ktor.routing.post
import java.util.UUID

fun Application.profileRoutes() {
  routing {
    route("profiles") {
      post {
        val profileDTO = call.receive<CreateProfileRequestDTO>()
        val profileCreated = createProfileUseCase.execute(profileDTO)
        call.respond(profileCreated)
      }

      get("{id}") {
        val id = UUID.fromString(call.parameters["id"])
        val profile = findProfileByIdUseCase.execute(id)
        if (profile != null) {
          call.respond(profile)
        } else {
          call.respond(HttpStatusCode.BadRequest, "User not found")
        }
      }
    }
  }
}