package com.pkxd.data.usecases.profile

import com.pkxd.data.interfaces.idGenerator.IdGenerator
import com.pkxd.data.interfaces.repository.ProfileRepository
import com.pkxd.domain.model.Profile
import com.pkxd.dtos.profile.CreateProfileRequestDTO
import com.pkxd.dtos.profile.CreateProfileResponseDTO

class CreateProfileUseCase(private val profileRepository: ProfileRepository, private val idGenerator: IdGenerator) {
  suspend fun execute(profileDTO: CreateProfileRequestDTO): CreateProfileResponseDTO {
    val newId = idGenerator.generate()
    val profile = Profile(newId, nickname = profileDTO.nickname, email = profileDTO.email)
    profileRepository.add(profile)
    return CreateProfileResponseDTO(newId.toString())
  }
}