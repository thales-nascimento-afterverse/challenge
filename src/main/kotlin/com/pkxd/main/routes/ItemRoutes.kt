package com.pkxd.main.routes

import com.pkxd.dtos.item.CreateItemRequestDTO
import com.pkxd.dtos.item.CreateItemResponseDTO
import com.pkxd.dtos.item.UpdateItemDTO
import com.pkxd.main.config.Configuration.createItemUseCase
import com.pkxd.main.config.Configuration.deleteItemUseCase
import com.pkxd.main.config.Configuration.updateItemUseCase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.delete
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import io.ktor.routing.routing
import java.util.UUID

fun Application.itemRoutes() {
  routing {
    route("items") {
      post {
        val itemDTO = call.receive<CreateItemRequestDTO>()
        val item = createItemUseCase.execute(itemDTO)
        val response = CreateItemResponseDTO(item.id.toString())
        call.respond(response)
      }

      put("{id}") {
        val id = UUID.fromString(call.parameters["id"])
        val itemDTO = call.receive<UpdateItemDTO>()
        updateItemUseCase.execute(id, itemDTO)
        call.respond(HttpStatusCode.NoContent)
      }

      delete("{id}") {
        val id = UUID.fromString(call.parameters["id"])
        deleteItemUseCase.execute(id)
        call.respond(HttpStatusCode.NoContent)
      }
    }
  }
}