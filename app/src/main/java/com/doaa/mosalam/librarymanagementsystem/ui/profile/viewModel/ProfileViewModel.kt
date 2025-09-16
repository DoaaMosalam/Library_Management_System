package com.doaa.mosalam.librarymanagementsystem.ui.profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaa.mosalam.domain.model.register.Register
import com.doaa.mosalam.domain.useCase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<Register?>(null)
    val user: StateFlow<Register?> = _user

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _passwordChangeState = MutableStateFlow<String?>(null)
    val passwordChangeState: StateFlow<String?> = _passwordChangeState


    // loading user data
    fun loadUser(email: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val userData = profileUseCase.getUserByEmail(email)
                _user.value = userData
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    // delete user

    fun deleteUser(email: String) {
        viewModelScope.launch {
            try {
                profileUseCase.deleteUserByEmail(email)
                _user.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }


    // update password
    fun changePassword(email: String, newPassword: String) {
        viewModelScope.launch {
            try {
                profileUseCase.updatePassword(email, newPassword)
                _passwordChangeState.value = "Password updated successfully"
            } catch (e: Exception) {
                _passwordChangeState.value = "Failed: ${e.message}"
            }
        }
    }


}