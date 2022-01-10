package com.pkxd.main.routes

import com.pkxd.dtos.profile.CreateProfileRequestDTO
import com.pkxd.main.config.Configuration.createProfileUseCase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.routing.route
import io.ktor.routing.post

fun Application.profileRoutes() {
  routing {
    route("profiles") {
      post {
        val profileDTO = call.receive<CreateProfileRequestDTO>()
        val profileCreated = createProfileUseCase.execute(profileDTO)
        call.respond(profileCreated)
      }
    }
  }
}