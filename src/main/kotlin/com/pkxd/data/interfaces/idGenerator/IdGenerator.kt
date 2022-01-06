package com.pkxd.data.interfaces.idGenerator

import java.util.UUID

interface IdGenerator {
  fun generate(): UUID
}