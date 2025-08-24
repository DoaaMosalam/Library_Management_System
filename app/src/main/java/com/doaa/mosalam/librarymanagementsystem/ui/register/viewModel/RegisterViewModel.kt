package com.doaa.mosalam.librarymanagementsystem.ui.register.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doaa.mosalam.domain.model.register.Register
import com.doaa.mosalam.domain.useCase.RegisterUseCase
import com.doaa.mosalam.librarymanagementsystem.utils.isEmailValid
import com.doaa.mosalam.librarymanagementsystem.utils.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _name = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _phone = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _confirmPassword = MutableStateFlow("")

    private val _isRegisterEnabled = MutableStateFlow(false)
    val isRegisterEnabled: StateFlow<Boolean> = _isRegisterEnabled

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    sealed interface UiState {
        data object Idle : UiState
        data object Loading : UiState
        data object Success : UiState
        data class Error(val message: String) : UiState
    }

    fun onNameChanged(newName: String) {
        _name.value = newName
        validateInputs()
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        validateInputs()
    }

    fun onPhoneChanged(newPhone: String) {
        _phone.value = newPhone
        validateInputs()
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        validateInputs()
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        validateInputs()
    }

    private fun validateInputs() {
        _isRegisterEnabled.value =
            _name.value.isNotEmpty() &&
                    _email.value.isEmailValid() &&
                    _phone.value.isNotEmpty() &&
                    _password.value.isPasswordValid() &&
                    _confirmPassword.value == _password.value
    }

    fun registerUser() {
        if (!_isRegisterEnabled.value) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                registerUseCase.registerUser(
                    Register(
                        name = _name.value,
                        email = _email.value,
                        phone = _phone.value,
                        password = _password.value,
                        confirmPassword = _confirmPassword.value
                    )
                )
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unexpected error")
            }
        }
    }
}