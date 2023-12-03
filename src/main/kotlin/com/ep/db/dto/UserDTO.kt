package com.ep.db.dto

import com.auth0.jwt.JWT
import com.ep.db.DatabaseConstants
import com.ep.db.DatabaseHelper
import com.ep.db.entity.BaseResponse
import com.ep.db.entity.user_register.UserLoginRequest
import com.ep.db.entity.user_register.UserRegisterRequest
import com.ep.db.entity.user_register.response.UserLoginResponse
import com.ep.security.JwtConfig
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.mindrot.jbcrypt.BCrypt

class UserDTO {
    private val database = DatabaseHelper.getDatabase()
    private val userCollection: CoroutineCollection<UserRegisterRequest>? = database?.getCollection(DatabaseConstants.USER_COLLECTION_NAME)


    suspend fun registerUser(userRegister: UserRegisterRequest): UserRegisterRequest {
        userRegister.encryptPassword()
        userCollection?.insertOne(userRegister)
        return userRegister
    }

    suspend fun deleteUser(id: String): Boolean {
        return userCollection?.deleteOne(UserRegisterRequest::id eq id)?.wasAcknowledged()?:false
    }

    suspend fun changeUserDetails(userRegister: UserRegisterRequest): Boolean {
        val currentDetails = getUserById(userRegister.id)
        return if(currentDetails!=null){
            userRegister.username?.let{
                currentDetails.username = it
            }
            userRegister.email?.let{
                currentDetails.email = it
            }
            currentDetails.email = userRegister.email
            userCollection?.updateOne(UserRegisterRequest::id eq userRegister.id, currentDetails)?.wasAcknowledged()?:false

        }else{
            false
        }
    }

    private suspend fun getUserById(id:String):UserRegisterRequest?{
        return userCollection?.findOneById(id)
    }

    suspend fun loginUser(userLoginRequest: UserLoginRequest):BaseResponse<UserLoginResponse?>{
       val user = userCollection?.findOne(UserRegisterRequest::username eq userLoginRequest.username)
        if(user!=null){
            if(BCrypt.checkpw(userLoginRequest.password,user.password)){
                val token = JwtConfig.instance.createAccessToken(user.id)
                return BaseResponse(
                    UserLoginResponse(
                    user.id,
                    token,
                    user.email,
                    user.username
                ),true,"Logged in successful"
                )
            }else{
                return BaseResponse(null,false,"Incorrect password")
            }
        }else{
            return BaseResponse(null,false,"User doesn't exist")
        }

    }
}