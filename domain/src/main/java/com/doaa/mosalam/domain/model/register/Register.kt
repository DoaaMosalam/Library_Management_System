package com.doaa.mosalam.domain.model.register

data class Register(
    val name: String,
    val email: String,
    val phone:String,
    val password: String,
    val confirmPassword: String
)
