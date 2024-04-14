package edu.uoc.avalldeperas.eatsafe.auth.register.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.register.domain.model.RegisterInputValidationType
import edu.uoc.avalldeperas.eatsafe.auth.register.domain.use_cases.ValidateRegisterInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateRegisterInputUseCase: ValidateRegisterInputUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateConfirmPassword(newPassword: String) {
        _confirmPassword.value = newPassword
    }

    fun onRegisterClick(navigateToExplore: () -> Unit) {
        val validationResult =
            validateRegisterInputUseCase(_email.value, _password.value, _confirmPassword.value)
        Log.d(
            "avb", "validation result: ${validationResult.isValid} and ${validationResult.message}"
        )
        if (validationResult == RegisterInputValidationType.Valid) {
            viewModelScope.launch {
                val authResult = authRepository.signUp(_email.value, _password.value)
                if (authResult) {
                    navigateToExplore()
                } else {
                    Log.d("avb", "onLoginClick: failed result")
                }
            }
        }
    }
}