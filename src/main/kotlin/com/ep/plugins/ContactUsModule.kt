package com.ep.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.contactUsModule() {
    routing {
        get("/contact-us") {
            call.respondText("Thanks for contacting us.")
        }

        get("/about-us") {
            call.respondText("Thanks for visiting about us.")
        }
    }
}