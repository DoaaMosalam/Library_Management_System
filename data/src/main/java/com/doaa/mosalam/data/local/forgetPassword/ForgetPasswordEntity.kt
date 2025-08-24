package com.doaa.mosalam.data.local.forgetPassword

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForgetPasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val email: String,
)
