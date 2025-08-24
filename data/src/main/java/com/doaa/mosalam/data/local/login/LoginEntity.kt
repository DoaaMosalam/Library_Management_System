package com.doaa.mosalam.data.local.login

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Login_table")
data class LoginEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val email: String,
    val password: String,
)
