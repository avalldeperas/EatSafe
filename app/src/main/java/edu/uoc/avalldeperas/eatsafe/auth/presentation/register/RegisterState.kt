package edu.uoc.avalldeperas.eatsafe.auth.presentation.register

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