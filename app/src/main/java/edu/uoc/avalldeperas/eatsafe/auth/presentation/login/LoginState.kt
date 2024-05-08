package edu.uoc.avalldeperas.eatsafe.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordShown: Boolean = false,
    val isLoading: Boolean = false
)