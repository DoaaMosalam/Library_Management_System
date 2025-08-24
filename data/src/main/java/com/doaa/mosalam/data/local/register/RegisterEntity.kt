package com.doaa.mosalam.data.local.register

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "register_table")
data class RegisterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val confirmPassword: String
)
