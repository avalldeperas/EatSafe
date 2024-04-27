package edu.uoc.avalldeperas.eatsafe.auth.login.presentation.state

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordShown: Boolean = false,
    val isLoading: Boolean = false
)