package edu.uoc.avalldeperas.eatsafe.auth.register.presentation.state

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val username: String = "",
    val currentCity: String = "",
    val isPasswordShown: Boolean = false,
    val isConfirmPasswordShown: Boolean = false,
    val isLoading: Boolean = false
)