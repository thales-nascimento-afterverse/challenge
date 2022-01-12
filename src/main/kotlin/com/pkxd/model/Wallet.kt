package com.pkxd.model

data class Wallet(val coins: Int = 0, val gems: Int = 0) {
  companion object {
    val EMPTY = Wallet()
  }
}

operator fun Wallet.plus(other: Wallet) = Wallet(
  coins + other.coins,
  gems + other.gems
)