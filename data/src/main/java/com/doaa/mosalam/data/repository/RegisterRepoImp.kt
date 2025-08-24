package com.doaa.mosalam.data.repository

import com.doaa.mosalam.data.local.register.RegisterDAO
import com.doaa.mosalam.data.mapper.toEntity
import com.doaa.mosalam.domain.model.register.Register
import com.doaa.mosalam.domain.repo.RegisterRepo

class RegisterRepoImp(private val registerDAO: RegisterDAO): RegisterRepo {
    override suspend fun RegisterUser(register: Register) {
        val registerEntity = register.toEntity()
        registerDAO.insertRegister(registerEntity)

    }

}