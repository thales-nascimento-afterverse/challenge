package com.pkxd.data.interfaces.repository

import com.pkxd.model.Profile

interface ProfileRepository {
  suspend fun add(profile: Profile)
}