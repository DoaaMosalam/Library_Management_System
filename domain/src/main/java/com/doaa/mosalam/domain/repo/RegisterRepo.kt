package com.doaa.mosalam.domain.repo

import com.doaa.mosalam.domain.model.register.Register

interface RegisterRepo {
    suspend fun RegisterUser(register: Register)
}