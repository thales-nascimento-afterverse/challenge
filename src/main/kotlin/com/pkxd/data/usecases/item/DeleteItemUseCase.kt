package com.pkxd.data.usecases.item

import com.pkxd.data.interfaces.repository.ItemRepository
import java.util.UUID

class DeleteItemUseCase(private val itemRepository: ItemRepository) {
  suspend fun execute(id: UUID) {
    itemRepository.delete(id)
  }
}