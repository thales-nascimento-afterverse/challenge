package com.pkxd.data.interfaces.repository

import com.pkxd.model.Item
import java.util.UUID

interface ItemRepository {
  suspend fun add(item: Item)
  suspend fun delete(id: UUID)
  suspend fun update(item: Item)
}