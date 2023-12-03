package com.ep.db.entity.user_register

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UserRegisterRequest(
    @BsonId
    val id:String = ObjectId().toString(),
    var username:String,
    var password:String,
    var email:String
){
    fun encryptPassword(){
        password =  BCrypt.hashpw(password,BCrypt.gensalt())
    }
}


@Serializable
data class UserLoginRequest(
    var username:String,
    var password:String
){
    fun encryptPassword(){
        password =  BCrypt.hashpw(password,BCrypt.gensalt())
    }
}