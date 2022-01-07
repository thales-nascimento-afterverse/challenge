package com.pkxd.main.routes

import com.pkxd.dtos.product.CreateProductRequestDTO
import com.pkxd.main.config.Configuration.createProductUseCase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.routing
import io.ktor.routing.route
import io.ktor.routing.post
import io.ktor.request.receive
import io.ktor.response.respond


fun Application.productRoutes() {
  routing {
    route("products") {
      post {
        val productDTO = call.receive<CreateProductRequestDTO>()
        println(productDTO.price)
        val productCreated = createProductUseCase.execute(productDTO)
        call.respond(productCreated)
      }
    }
  }
}