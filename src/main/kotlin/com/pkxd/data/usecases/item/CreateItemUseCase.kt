package com.pkxd.data.usecases.item

import com.pkxd.data.interfaces.repository.ItemRepository
import com.pkxd.model.Item
import com.pkxd.dtos.item.CreateItemRequestDTO
import java.util.UUID

class CreateItemUseCase(private val itemRepository: ItemRepository) {
  suspend fun execute(createItemRequestDTO: CreateItemRequestDTO): Item {
    val id = UUID.randomUUID()
    val item = Item(
      id,
      coins = createItemRequestDTO.coins,
      gems = createItemRequestDTO.gems,
      createItemRequestDTO.image
    )
    itemRepository.add(item)
    return item
  }
}