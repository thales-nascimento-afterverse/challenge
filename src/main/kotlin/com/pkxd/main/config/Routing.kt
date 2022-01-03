package com.pkxd.main.config

import io.ktor.application.*
import com.pkxd.main.routes.profileRoutes

fun Application.configureRouting() {
    profileRoutes()
}
