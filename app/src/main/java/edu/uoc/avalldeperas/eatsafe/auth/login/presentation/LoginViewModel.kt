package edu.uoc.avalldeperas.eatsafe.auth.login.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.use_cases.ValidateLoginInputUseCase
import edu.uoc.avalldeperas.eatsafe.auth.login.presentation.state.LoginState
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateLoginInputUseCase: ValidateLoginInputUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun updateEmail(newEmail: String) {
        _loginState.update { currentState -> currentState.copy(email = newEmail) }
    }

    fun updatePassword(newPassword: String) {
        _loginState.update { currentState -> currentState.copy(password = newPassword) }
    }

    fun onToggleVisualTransformationPassword() {
        _loginState.update { currentState -> currentState.copy(isPasswordShown = !currentState.isPasswordShown) }
    }

    fun onLoginClick(onSubmit: () -> Unit, context: Context) {
        val validationResult =
            validateLoginInputUseCase(_loginState.value.email, _loginState.value.password)

        if (!validationResult.isValid) {
            showToast(validationResult.message!!, context)
            return
        }

        viewModelScope.launch {
            _loginState.update { currentState -> currentState.copy(isLoading = true) }
            val errorMessage =
                authRepository.signIn(_loginState.value.email, _loginState.value.password)
            _loginState.update { currentState -> currentState.copy(isLoading = false) }
            if (errorMessage.isNotEmpty()) {
                showToast(errorMessage, context)
                return@launch
            }

            onSubmit()
        }
    }
}