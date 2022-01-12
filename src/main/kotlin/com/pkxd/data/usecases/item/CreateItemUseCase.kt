package com.pkxd.data.usecases.item

import com.pkxd.data.interfaces.idGenerator.IdGenerator
import com.pkxd.data.interfaces.repository.ItemRepository
import com.pkxd.model.Item
import com.pkxd.dtos.item.CreateItemRequestDTO
import java.util.UUID

class CreateItemUseCase(private val itemRepository: ItemRepository, private val idGenerator: IdGenerator<UUID>) {
  suspend fun execute(createItemRequestDTO: CreateItemRequestDTO): Item {
    val id = idGenerator.generate()
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