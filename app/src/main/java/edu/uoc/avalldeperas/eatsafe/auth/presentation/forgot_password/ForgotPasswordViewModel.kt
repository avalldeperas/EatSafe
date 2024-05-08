package edu.uoc.avalldeperas.eatsafe.auth.presentation.forgot_password

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.domain.use_cases.ValidateForgotPasswordUseCase
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateForgotPasswordInputUseCase: ValidateForgotPasswordUseCase
) : ViewModel() {

    private val _forgotState = MutableStateFlow(ForgotPasswordState())
    val forgotState = _forgotState.asStateFlow()

    fun updateEmail(newEmail: String) {
        _forgotState.update { currentState -> currentState.copy(email = newEmail) }
    }

    fun onForgotClick(context: Context) {
        val validationResult = validateForgotPasswordInputUseCase(_forgotState.value.email)

        if (!validationResult.isValid) {
            showToast(validationResult.message!!, context)
            return
        }

        viewModelScope.launch {
            _forgotState.update { currentState -> currentState.copy(isLoading = true) }
            val errorMessage = authRepository.passwordReset(_forgotState.value.email)
            if (errorMessage.isNotEmpty()) {
                showToast(errorMessage, context)
                _forgotState.update { currentState -> currentState.copy(isLoading = false) }
                return@launch
            }

            _forgotState.update { currentState -> currentState.copy(isLoading = false) }
            showToast("Recovery password email sent", context)
        }
    }
}