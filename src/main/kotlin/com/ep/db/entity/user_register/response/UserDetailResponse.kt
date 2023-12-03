package com.ep.db.entity.user_register.response

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterResponse(
    val id: String,
    val email: String,
    val username: String
)

@Serializable
data class UserLoginResponse(
    val id: String,
    val token: String,
    val email: String,
    val username: String
)