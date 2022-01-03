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
            try {
                val profileDTO = call.receive<CreateProfileRequestDTO>()
                val profileCreated = createProfileUseCase.execute(profileDTO)
                call.respond(profileCreated)
            }catch (exception: SerializationException) {
                call.respondText("Campos obrigatórios não foram enviados", null, HttpStatusCode.BadRequest)
            }
            catch (exception: Exception) {
                call.respondText("",null, HttpStatusCode.BadRequest)
            }
        }
    }
}