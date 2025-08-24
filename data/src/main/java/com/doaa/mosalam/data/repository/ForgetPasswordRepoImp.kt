package com.doaa.mosalam.data.repository

import com.doaa.mosalam.domain.repo.ForgetPasswordRepo

class ForgetPasswordRepoImp(

) : ForgetPasswordRepo{
    override suspend fun forgetPasswordUser(email: String): Boolean {

        // Since there's no actual data source, we'll simulate the operation.
        // In a real implementation, you would check if the email exists in your data source.
        return email.isNotEmpty() // Simulate success if email is not empty
    }
}