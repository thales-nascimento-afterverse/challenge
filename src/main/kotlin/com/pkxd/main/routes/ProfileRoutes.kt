package com.pkxd.main.routes

import com.pkxd.dtos.CreateProfileRequestDTO
import com.pkxd.main.config.AWSConfiguration
import com.pkxd.main.config.Configuration.createProfileUseCase
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.SerializationException

fun Application.profileRoutes() {
    routing {
        get("/profile/{id}") {
            call.respondText("Teste Rotas arquivo")
        }

        post("/profile") {
            val profileDTO = call.receive<CreateProfileRequestDTO>()
            val profileCreated = createProfileUseCase.execute(profileDTO)
            call.respond(profileCreated)
        }
    }
}