package com.doaa.mosalam.domain.repo

import com.doaa.mosalam.domain.model.login.Login

interface LoginRepo {
    suspend fun LoginUser(login: Login): Login?
}