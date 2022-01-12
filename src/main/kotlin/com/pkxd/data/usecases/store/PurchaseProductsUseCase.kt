package com.pkxd.data.usecases.store

import com.pkxd.data.interfaces.repository.ProductRepository
import com.pkxd.data.interfaces.repository.ProfileRepository
import com.pkxd.dtos.store.PurchaseProductsDTO
import com.pkxd.utils.errors.EntityNotFoundException
import java.util.UUID

class PurchaseProductsUseCase(
  private val profileRepository: ProfileRepository,
  private val productRepository: ProductRepository
) {
  suspend fun processPurchase(purchaseProductsDTO: PurchaseProductsDTO) {
    val id = UUID.fromString(purchaseProductsDTO.userId)
    val profile = profileRepository.findById(id)
      ?: throw EntityNotFoundException("User ${purchaseProductsDTO.userId} not found")

    val productsIds = purchaseProductsDTO.products.map { UUID.fromString(it.productId) }
    val products = productRepository.fetchProductsByIds(productsIds)


  }
}