package com.pkxd.data.interfaces.repository

import com.pkxd.model.Profile
import com.pkxd.model.Wallet
import java.util.UUID

interface ProfileRepository {
  suspend fun add(profile: Profile)
  suspend fun findById(id: UUID): Profile?
  suspend fun updateProfileWallet(profileId: UUID, wallet: Wallet)
}