package com.pkxd.main.routes

import com.pkxd.dtos.store.PurchaseProductsDTO
import com.pkxd.main.config.Configuration.purchaseProductsUseCase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing

fun Application.storeRoutes() {
  routing {
    route("store/purchase") {
      post("products") {
        val purchaseDTO = call.receive<PurchaseProductsDTO>()
        purchaseProductsUseCase.processPurchase(purchaseDTO)
        call.respond(HttpStatusCode.NoContent)
      }
    }
  }
}