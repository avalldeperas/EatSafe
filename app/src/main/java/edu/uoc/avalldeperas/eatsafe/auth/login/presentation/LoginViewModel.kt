package edu.uoc.avalldeperas.eatsafe.auth.login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.LoginInputValidationType
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.use_cases.ValidateLoginInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateLoginInputUseCase: ValidateLoginInputUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClick(onSubmit: () -> Unit) {
        val validationResult = validateLoginInputUseCase(_email.value, _password.value)
        Log.d("avb", "validation result: ${validationResult.isValid} and ${validationResult.message}")
        if (validationResult == LoginInputValidationType.Valid) {
            viewModelScope.launch {
                val authResult = authRepository.signIn(_email.value, _password.value)
                if (authResult) {
                    onSubmit()
                } else {
                    Log.d("avb", "onLoginClick: failed result")
                }
            }
        }
    }
}