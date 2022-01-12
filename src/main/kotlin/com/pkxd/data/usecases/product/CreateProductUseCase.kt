package com.pkxd.data.usecases.product

import com.pkxd.data.interfaces.idGenerator.IdGenerator
import com.pkxd.data.interfaces.repository.ProductRepository
import com.pkxd.dtos.product.CreateProductRequestDTO
import com.pkxd.dtos.product.CreateProductResponseDTO
import com.pkxd.model.Product
import java.util.UUID

class CreateProductUseCase(
  private val productRepository: ProductRepository,
  private val idGenerator: IdGenerator<UUID>
) {
  suspend fun execute(createProductRequestDTO: CreateProductRequestDTO): CreateProductResponseDTO {
    val newId = idGenerator.generate()
    val product = Product(
      newId,
      coins = createProductRequestDTO.coins,
      gems = createProductRequestDTO.gems,
      price = createProductRequestDTO.price
    )
    productRepository.add(product)
    return CreateProductResponseDTO(newId.toString())
  }
}