package com.pkxd.dtos.store.product

data class PurchaseProductsRequestDTO(val userId: String, val products: List<PurchaseProductItemDTO>)
