package com.doaa.mosalam.data.repository

import com.doaa.mosalam.data.local.register.RegisterDAO
import com.doaa.mosalam.data.mapper.toDomain
import com.doaa.mosalam.domain.model.register.Register
import com.doaa.mosalam.domain.repo.ProfileRepo
import javax.inject.Inject

class ProfileRepoImp @Inject constructor(
    private val registerDAO: RegisterDAO
) : ProfileRepo{
    override suspend fun getUserByEmail(email: String): Register? = registerDAO.getUserByEmail(email)?.toDomain()

    override suspend fun deleteUserByEmail(email: String) = registerDAO.deleteUserByEmail(email)
    override suspend fun updatePassword(email: String, newPassword: String) = registerDAO.updatePassword(email,newPassword)


}