package com.pkxd.model

data class Wallet(val coins: Int = 0, val gems: Int = 0) {
  companion object {
    val EMPTY = Wallet()
  }
}