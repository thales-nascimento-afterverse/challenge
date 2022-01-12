package com.pkxd.model

import java.util.UUID

data class Profile(val id: UUID, val nickname: String, val email: String, val wallet: Wallet)