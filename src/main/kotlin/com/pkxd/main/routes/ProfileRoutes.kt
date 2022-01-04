package com.pkxd.main.routes

import com.pkxd.dtos.CreateProfileRequestDTO
import com.pkxd.main.config.Configuration.createProfileUseCase
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.profileRoutes() {
  routing {
    route("profile"){
      get("{id}") {
        call.respondText("Teste Rotas arquivo")
      }

      post {
        val profileDTO = call.receive<CreateProfileRequestDTO>()
        val profileCreated = createProfileUseCase.execute(profileDTO)
        call.respond(profileCreated)
      }
    }
  }
}