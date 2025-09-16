package com.doaa.mosalam.domain.useCase

import com.doaa.mosalam.domain.repo.ProfileRepo
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepo: ProfileRepo
) {
    suspend fun getUserByEmail(email: String) = profileRepo.getUserByEmail(email)

    suspend fun deleteUserByEmail(email: String) = profileRepo.deleteUserByEmail(email)

    suspend fun updatePassword(email: String,newPassword: String) = profileRepo.updatePassword(email,newPassword)
}