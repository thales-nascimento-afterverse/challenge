package com.pkxd.data.interfaces.repository

import com.pkxd.domain.model.Product
import java.util.UUID

interface ProductRepository {
  suspend fun add(product: Product)
  suspend fun delete(id: UUID)
  suspend fun update(product: Product)
}