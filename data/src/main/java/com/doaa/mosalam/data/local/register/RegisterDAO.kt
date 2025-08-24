package com.doaa.mosalam.data.local.register

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RegisterDAO {
    // // save info user after register
    @Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    suspend fun insertRegister(registerEntity: RegisterEntity): Long

    @Query("SELECT * FROM register_table WHERE name = :name LIMIT 1")
    suspend fun getUserByName(name: String):RegisterEntity?

    @Query("SELECT * FROM register_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): RegisterEntity?
}