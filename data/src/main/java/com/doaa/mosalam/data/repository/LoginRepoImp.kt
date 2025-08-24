package com.doaa.mosalam.data.repository

import com.doaa.mosalam.data.local.login.LoginDAO
import com.doaa.mosalam.data.local.login.LoginEntity
import com.doaa.mosalam.data.local.register.RegisterDAO
import com.doaa.mosalam.data.mapper.toLoginDomain

import com.doaa.mosalam.domain.model.login.Login
import com.doaa.mosalam.domain.repo.LoginRepo


class LoginRepoImp(
    private val registerDAO: RegisterDAO,
    private val loginDAO: LoginDAO
) : LoginRepo {

    override suspend fun LoginUser(login: Login): Login? {
        // التحقق من بيانات المستخدم في جدول register
        val registerEntity = registerDAO.getUserByEmailAndPassword(login.email, login.password)

        return if (registerEntity != null) {
            // حفظ بيانات المستخدم في جدول login بعد النجاح
            val loginEntity = LoginEntity(
                email = registerEntity.email,
                password = registerEntity.password
            )
            loginDAO.insertLogin(loginEntity)
            loginEntity.toLoginDomain() // تحويل لـ domain
        } else {
            null
        }
    }
}