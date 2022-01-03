package com.pkxd.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreateProfileRequestDTO (val nickname: String, val email: String)


