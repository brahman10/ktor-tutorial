package com.ep.db.entity

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val data:T,
    val success:Boolean,
    val msg:String=""
)
