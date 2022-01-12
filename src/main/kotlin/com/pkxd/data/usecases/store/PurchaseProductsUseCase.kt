package com.pkxd.data.usecases.store

import com.pkxd.data.interfaces.repository.ProductRepository
import com.pkxd.data.interfaces.repository.ProfileRepository
import com.pkxd.dtos.store.product.PurchaseProductItemDTO
import com.pkxd.dtos.store.product.PurchaseProductResponseDTO
import com.pkxd.dtos.store.product.PurchaseProductsRequestDTO
import com.pkxd.model.Product
import com.pkxd.model.Wallet
import com.pkxd.model.plus
import com.pkxd.utils.errors.EntityNotFoundException
import java.util.UUID

class PurchaseProductsUseCase(
  private val profileRepository: ProfileRepository,
  private val productRepository: ProductRepository
) {
  suspend fun processPurchase(purchaseProductsRequestDTO: PurchaseProductsRequestDTO): PurchaseProductResponseDTO {
    val profileId = UUID.fromString(purchaseProductsRequestDTO.userId)
    val profile = profileRepository.findById(profileId)
      ?: throw EntityNotFoundException("User ${purchaseProductsRequestDTO.userId} not found")

    val productsIds = purchaseProductsRequestDTO.products.map { UUID.fromString(it.productId) }
    val fullInfoProducts = productRepository.fetchProductsByIds(productsIds)
    val productsQuantity = purchaseProductsRequestDTO.products.associate { it.productId to it.quantity }

    val purchasePrice = fullInfoProducts.fold(0.0) { acc, product ->
      acc + product.price
    }
    val totalPurchasedCurrencyWallet = fullInfoProducts.fold(Wallet()) { acc, product ->
      acc + product.toWallet(productsQuantity[product.id.toString()] ?: 1)
    }

    profileRepository.updateProfileWallet(profileId, totalPurchasedCurrencyWallet)

    return PurchaseProductResponseDTO(
      userId = purchaseProductsRequestDTO.userId,
      products = fullInfoProducts.map {
        PurchaseProductItemDTO(
          it.id.toString(),
          productsQuantity[it.id.toString()] ?: 1
        )
      }.toList(),
      Wallet = profile.wallet + totalPurchasedCurrencyWallet,
      purchaseTotal = purchasePrice
    )
  }
}

private fun Product.toWallet(quantity: Int) = Wallet(
  coins = this.coins * quantity,
  gems = this.gems * quantity
)