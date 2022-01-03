package com.pkxd

import io.ktor.application.*
import com.pkxd.main.config.*
import com.pkxd.main.config.configureHTTP
import com.pkxd.main.config.configureRouting
import com.pkxd.main.config.configureSerialization

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureHTTP()
    configureRouting()
    configureSerialization()
}
