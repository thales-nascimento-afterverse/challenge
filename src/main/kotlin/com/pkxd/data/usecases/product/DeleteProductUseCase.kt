package com.pkxd.data.usecases.product

import com.pkxd.data.interfaces.repository.ProductRepository
import java.util.UUID

class DeleteProductUseCase(private val productRepository: ProductRepository) {
  suspend fun execute(id: UUID) {
    productRepository.delete(id)
  }
}