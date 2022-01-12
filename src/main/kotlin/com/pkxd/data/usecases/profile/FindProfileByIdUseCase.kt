package com.pkxd.data.usecases.profile

import com.pkxd.data.interfaces.repository.ProfileRepository
import com.pkxd.model.Profile
import java.util.UUID

class FindProfileByIdUseCase(private val profileRepository: ProfileRepository) {
  suspend fun execute(id: UUID): Profile? = profileRepository.findById(id)
}