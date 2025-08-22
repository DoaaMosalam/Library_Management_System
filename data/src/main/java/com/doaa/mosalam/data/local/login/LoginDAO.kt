package com.doaa.mosalam.data.local.login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoginDAO {
    // insert info user after login
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertLogin(loginEntity: LoginEntity): Long

    // return user in login data
   @Query("SELECT * FROM login_table WHERE email = :email AND password = :password")
    suspend fun getLogin(email: String, password: String): LoginEntity?


    // delete user from login data
    @Query("DELETE FROM login_table WHERE email = :email")
    suspend fun logout(email: String)

}