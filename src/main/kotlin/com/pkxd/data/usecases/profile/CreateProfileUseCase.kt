package com.pkxd.data.usecases.profile

import com.pkxd.data.interfaces.idGenerator.IdGenerator
import com.pkxd.data.interfaces.repository.ProfileRepository
import com.pkxd.model.Profile
import com.pkxd.dtos.profile.CreateProfileRequestDTO
import com.pkxd.dtos.profile.CreateProfileResponseDTO
import com.pkxd.model.Wallet
import java.util.UUID

class CreateProfileUseCase(
  private val profileRepository: ProfileRepository,
  private val idGenerator: IdGenerator<UUID>
) {
  suspend fun execute(profileDTO: CreateProfileRequestDTO): CreateProfileResponseDTO {
    val newId = idGenerator.generate()
    val profile = Profile(
      id = newId,
      nickname = profileDTO.nickname,
      email = profileDTO.email,
      wallet = Wallet.EMPTY
    )
    profileRepository.add(profile)
    return CreateProfileResponseDTO(newId.toString())
  }
}