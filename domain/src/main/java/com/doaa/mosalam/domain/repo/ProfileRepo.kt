package com.doaa.mosalam.domain.repo

import com.doaa.mosalam.domain.model.register.Register

interface ProfileRepo {

    suspend fun getUserByEmail(email: String): Register?
    suspend fun deleteUserByEmail(email: String)

    // update password
    suspend fun updatePassword(email: String, newPassword: String)

}