package com.doaa.mosalam.librarymanagementsystem.ui.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaa.mosalam.domain.model.login.Login
import com.doaa.mosalam.domain.useCase.LoginUseCase
import com.doaa.mosalam.librarymanagementsystem.utils.isEmailValid
import com.doaa.mosalam.librarymanagementsystem.utils.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // ---- Sign In button state ----
    private val _isSignInEnabled = MutableStateFlow(false)
    val isSignInEnabled: StateFlow<Boolean> = _isSignInEnabled

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    // ---- UI state ----
    sealed interface UiState {
        data object Idle : UiState
        data object Loading : UiState
        data class Success(val user: Login) : UiState
        data class Error(val message: String) : UiState
    }

    // ---- Updaters ----
    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail.trim()
        validateInputs()
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        validateInputs()
    }

    private fun validateInputs() {
//        _isSignInEnabled.value =
//            _email.value.isNotEmpty() && _password.value.isNotEmpty()
        _isSignInEnabled.value =
            _email.value.isNotEmpty() &&
                    _password.value.isNotEmpty() &&
                    _email.value.isEmailValid() &&
                    _password.value.isPasswordValid()
    }

    // ---- Action: Login ----
    fun loginUser() {
        if (!_isSignInEnabled.value) return
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = loginUseCase.loginUser(Login(email.value, password.value))
                if (result != null) {
                    _uiState.value = UiState.Success(result)
                } else {
                    _uiState.value = UiState.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unexpected error")
            }
        }
    }


}