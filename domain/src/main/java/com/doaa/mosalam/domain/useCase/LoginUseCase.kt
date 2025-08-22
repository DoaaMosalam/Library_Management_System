package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.model.login.Login
import com.doaa.mosalam.domain.repo.LoginRepo
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepo: LoginRepo) {
    suspend fun loginUser(login: Login)  = loginRepo.LoginUser(login)
}