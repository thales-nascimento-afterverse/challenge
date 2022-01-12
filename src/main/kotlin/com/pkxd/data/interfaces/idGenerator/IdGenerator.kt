package com.pkxd.data.interfaces.idGenerator

fun interface IdGenerator<T> {
  fun generate(): T
}