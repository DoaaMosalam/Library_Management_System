package com.doaa.mosalam.data.local.forgetPassword

import androidx.room.Dao
import androidx.room.Query
import com.doaa.mosalam.domain.model.register.Register

@Dao
interface ForgetPasswordDAO {
    // insert email user forget password

    @Query("SELECT * FROM register_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): Register?

    @Query("UPDATE register_table SET password = :newPassword WHERE email = :email")
    suspend fun updatePassword(email: String, newPassword: String): Int
}