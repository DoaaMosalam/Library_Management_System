package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.repo.ForgetPasswordRepo
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(private val forgetPasswordRepo: ForgetPasswordRepo) {

    suspend fun forgetPasswordUser(
        email: String
    ): Boolean = forgetPasswordRepo.forgetPasswordUser(email)
}