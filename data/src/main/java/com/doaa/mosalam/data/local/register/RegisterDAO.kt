package com.doaa.mosalam.data.local.register

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doaa.mosalam.domain.model.register.Register

@Dao
interface RegisterDAO {
    // // save info user after register
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRegister(registerEntity: RegisterEntity): Long

    @Query("SELECT * FROM register_table WHERE name = :name LIMIT 1")
    suspend fun getUserByName(name: String):RegisterEntity?

    @Query("SELECT * FROM register_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): RegisterEntity?

    @Query("SELECT * FROM register_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): RegisterEntity?


    @Query("DELETE FROM register_table WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)

    @Query("UPDATE register_table SET password = :newPassword WHERE email = :email")
    suspend fun updatePassword(email: String, newPassword: String)
}