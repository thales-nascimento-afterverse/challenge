package com.pkxd.data.usecases.product

import com.pkxd.data.interfaces.repository.ProductRepository
import com.pkxd.model.Product
import com.pkxd.dtos.product.UpdateProductRequestDTO
import java.util.UUID

class UpdateProductUseCase(private val productRepository: ProductRepository) {
  suspend fun execute(id: UUID, updateProductDTO: UpdateProductRequestDTO) {
    val product =
      Product(id, price = updateProductDTO.price, coins = updateProductDTO.coins, gems = updateProductDTO.gems)
    productRepository.update(product)
  }
}