package com.ep.db.entity

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Notes(
    @BsonId
    val id:String = ObjectId().toString(),
    val note:String,
    val author:String
)
