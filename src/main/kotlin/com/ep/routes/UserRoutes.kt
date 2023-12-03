package com.ep.routes

import com.ep.db.dto.UserDTO
import com.ep.db.entity.BaseResponse
import com.ep.db.entity.user_register.UserLoginRequest
import com.ep.db.entity.user_register.UserRegisterRequest
import com.ep.db.entity.user_register.response.UserRegisterResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(
    userDTO: UserDTO
) {

    route("/user") {

        post("/register") {
            val requestBody = call.receive<UserRegisterRequest>()

            try {
                val user = userDTO.registerUser(requestBody)
                call.respond(
                    HttpStatusCode.OK, BaseResponse(
                        UserRegisterResponse(
                            user.id,
                            user.email,
                            user.username
                        ),
                        true,
                        "User registered successfully."
                    )
                )
            } catch (e: Exception) {
                application.run { log.error("Failed to register user", e.message) }
                call.respond(
                    HttpStatusCode.InternalServerError, BaseResponse(
                        null,
                        false,
                        "Something went wrong"
                    )
                )
            }
        }

        put("/{id}") {
            val requestBody = call.receive<UserRegisterRequest>()

            try {
                val user = userDTO.changeUserDetails(requestBody)
                call.respond(
                    HttpStatusCode.OK, BaseResponse(
                        null,
                        true,
                        "User registered successfully."
                    )
                )
            } catch (e: Exception) {
                application.run { log.error("Failed to register user", e.message) }
                call.respond(
                    HttpStatusCode.InternalServerError, BaseResponse(
                        null,
                        false,
                        "Something went wrong"
                    )
                )
            }
        }

        post("/login") {
            val requestBody = call.receive<UserLoginRequest>()

            try {
                val user = userDTO.loginUser(requestBody)
                if (user.success) {
                    call.respond(
                        HttpStatusCode.OK, user
                    )
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest, user
                    )
                }

            } catch (e: Exception) {
                application.run { log.error("Failed to register user", e.message) }
                call.respond(
                    HttpStatusCode.InternalServerError, BaseResponse(
                        null,
                        false,
                        "Something went wrong"
                    )
                )
            }
        }

    }
}