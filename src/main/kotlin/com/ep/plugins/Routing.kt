package com.ep.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.fconfigureRouting() {
    routing {
        get("/") {
            call.respondText("Hey Yash")
        }
    }
}
