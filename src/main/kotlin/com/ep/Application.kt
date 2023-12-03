package com.ep

import com.ep.db.dto.NoteDTO
import com.ep.db.dto.UserDTO
import com.ep.db.entity.BaseResponse
import com.ep.routes.notesRoute
import com.ep.routes.userRoutes
import com.ep.security.JwtConfig
import com.ep.security.UserIdPrincipalForUser
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

const val INVALID_AUTHENTICATION_TOKEN = "Invalid authentication token, please login again"

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        configureSecurity()
        routing {
            userRoutes(UserDTO())
            authenticate {
                notesRoute(NoteDTO())
            }
        }
    }.start(wait = true)
}

fun Application.configureSecurity() {
    JwtConfig.initialize("my-story-app")
    install(Authentication) {
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asString()
                if (claim != null) {
                    UserIdPrincipalForUser(claim)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = BaseResponse(null, false, INVALID_AUTHENTICATION_TOKEN)
                )
            }
        }
    }
}
