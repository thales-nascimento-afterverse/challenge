package com.pkxd.infra.uuid

import com.pkxd.data.interfaces.idGenerator.IdGenerator
import java.util.UUID

object UUIDGenerator: IdGenerator {
    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}