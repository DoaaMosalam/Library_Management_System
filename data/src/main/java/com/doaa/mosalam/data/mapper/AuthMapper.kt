package com.doaa.mosalam.data.mapper

import com.doaa.mosalam.data.local.login.LoginEntity
import com.doaa.mosalam.data.local.register.RegisterEntity
import com.doaa.mosalam.domain.model.login.Login
import com.doaa.mosalam.domain.model.register.Register

// convert from login entity to Login domain model

fun LoginEntity.toDomain(): Login{
    return Login(
        email = this.email,
        password = this.password
    )
}

// convert from Login domain model to login entity

fun Login.toEntity(): LoginEntity {
    return LoginEntity(
        email = this.email,
        password = this.password
    )
}

//convert from Register entity to Register domain model

fun RegisterEntity.toDomain(): Register {
    return Register(
        name = name,
        phone = phone,
        email = email,
        password = password,
        confirmPassword = confirmPassword
    )
}

//convert from Register domain model to Register entity
fun Register.toEntity(): RegisterEntity {
    return RegisterEntity(
        name = name,
        phone = phone,
        email = email,
        password = password,
        confirmPassword = confirmPassword
    )
}

fun RegisterEntity.toLoginDomain(): Login {
    return Login(email = this.email,
        password = this.password
    )
}

fun LoginEntity.toLoginDomain(): Login {
    return Login(email = this.email,
        password = this.password
    )
}

