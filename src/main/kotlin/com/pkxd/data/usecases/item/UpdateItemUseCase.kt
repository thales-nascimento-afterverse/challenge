package com.pkxd.data.usecases.item

import com.pkxd.data.interfaces.repository.ItemRepository
import com.pkxd.model.Item
import com.pkxd.dtos.item.UpdateItemDTO
import java.util.UUID

class UpdateItemUseCase(private val itemRepository: ItemRepository) {
  suspend fun execute(id: UUID, updateItemDTO: UpdateItemDTO) {
    val item = Item(
      id,
      coins = updateItemDTO.coins,
      gems = updateItemDTO.gems,
      updateItemDTO.image
    )
    itemRepository.update(item)
  }
}