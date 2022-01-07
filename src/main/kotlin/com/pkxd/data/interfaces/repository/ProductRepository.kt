package com.pkxd.data.interfaces.repository

import com.pkxd.domain.model.Product

interface ProductRepository {
  suspend fun add(product: Product)
}