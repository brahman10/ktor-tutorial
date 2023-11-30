package com.ep.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.contactUsModule() {
    routing {
        get("/contact-us") {
            val contactUsEntity = call.receive<ContactUsEntity>()
            call.respondText("Hey ${contactUsEntity.name}, We will contact you soon at ${contactUsEntity.email} or ${contactUsEntity.mobileNumber}")
        }

        get("/about-us") {
            call.respondText("Thanks for visiting about us.")
        }
    }
}

@Serializable
data class ContactUsEntity(
    val name:String,
    val mobileNumber:String,
    val email:String
)