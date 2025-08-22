package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.model.register.Register
import com.doaa.mosalam.domain.repo.RegisterRepo
import javax.inject.Inject


class RegisterUseCase @Inject constructor(private val registerRepo: RegisterRepo) {
    suspend fun registerUser(register: Register)  = registerRepo.RegisterUser(register)
}