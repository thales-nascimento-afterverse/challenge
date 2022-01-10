package com.pkxd.main.routes

import com.pkxd.dtos.product.CreateProductRequestDTO
import com.pkxd.dtos.product.UpdateProductRequestDTO
import com.pkxd.main.config.Configuration.createProductUseCase
import com.pkxd.main.config.Configuration.deleteProductUseCase
import com.pkxd.main.config.Configuration.updateProductUseCase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.routing.routing
import io.ktor.routing.route
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.delete
import io.ktor.request.receive
import io.ktor.response.respond
import java.util.UUID


fun Application.productRoutes() {
  routing {
    route("products") {
      post {
        val productDTO = call.receive<CreateProductRequestDTO>()
        val productCreated = createProductUseCase.execute(productDTO)
        call.respond(productCreated)
      }

      put("{id}") {
        val id = UUID.fromString(call.parameters["id"])
        val productDTO = call.receive<UpdateProductRequestDTO>()
        updateProductUseCase.execute(id, productDTO)
        call.respond(HttpStatusCode.NoContent)
      }

      delete("{id}") {
        val productID = UUID.fromString(call.parameters["id"])
        deleteProductUseCase.execute(productID)
        call.respond(HttpStatusCode.NoContent)
      }

    }
  }
}