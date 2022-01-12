package com.pkxd.dtos.store.product

import com.pkxd.model.Wallet

data class PurchaseProductResponseDTO(
  val userId: String,
  val products: List<PurchaseProductItemDTO>,
  val Wallet: Wallet,
  val purchaseTotal: Double
)
