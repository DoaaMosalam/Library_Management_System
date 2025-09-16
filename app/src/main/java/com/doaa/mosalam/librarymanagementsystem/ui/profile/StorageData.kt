package com.doaa.mosalam.librarymanagementsystem.ui.profile

import android.content.Context


class UserPreferences(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser(
        email: String,
        password: String,
        name: String = "No Name",
        phone: String = "No Phone"
    ) {
        with(sharedPreferences.edit()) {
            putString("user_email", email)
            putString("user_password", password)
            putString("user_name", name)
            putString("user_phone", phone)
            apply()
        }
    }

    fun getUserEmail(): String? = sharedPreferences.getString("user_email", null)
    fun getUserName(): String? = sharedPreferences.getString("user_name", null)
    fun getUserPhone(): String? = sharedPreferences.getString("user_phone", null)

    fun getUser(): UserData {
        return UserData(
            email = sharedPreferences.getString("user_email", "No Email") ?: "No Email",
            password = sharedPreferences.getString("user_password", "No Password") ?: "No Password",
            name = sharedPreferences.getString("user_name", "No Name") ?: "No Name",
            phone = sharedPreferences.getString("user_phone", "No Phone") ?: "No Phone"
        )
    }

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }
}

data class UserData(
    val email: String,
    val password: String,
    val name: String,
    val phone: String
)