package com.pkxd.data.interfaces.repository

import com.pkxd.domain.model.Profile

interface ProfileRepository {
  suspend fun add(profile: Profile)
}