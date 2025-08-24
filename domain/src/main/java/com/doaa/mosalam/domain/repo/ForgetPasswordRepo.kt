package com.doaa.mosalam.domain.repo

interface ForgetPasswordRepo {
    suspend fun forgetPasswordUser(
        email: String
    ): Boolean
}