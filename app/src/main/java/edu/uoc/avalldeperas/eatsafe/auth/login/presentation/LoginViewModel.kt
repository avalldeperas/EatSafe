package edu.uoc.avalldeperas.eatsafe.auth.login.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.use_cases.ValidateLoginInputUseCase
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil.showToast
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClick(onSubmit: () -> Unit, context: Context) {
        val validationResult = validateLoginInputUseCase(_email.value, _password.value)

        if (!validationResult.isValid) {
            showToast(validationResult.message!!, context)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            val errorMessage = authRepository.signIn(_email.value, _password.value)
            _isLoading.value = false
            if (errorMessage.isNotEmpty()) {
                showToast(errorMessage, context)
                return@launch
            }

            onSubmit()
        }

    }
}