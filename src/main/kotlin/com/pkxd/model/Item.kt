package com.pkxd.model

import java.util.UUID

data class Item(val id: UUID, val coins: Int = 0, val gems: Int = 0, val image: String)
