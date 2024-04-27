package edu.uoc.avalldeperas.eatsafe.auth.forgot_password.presentation.state

data class ForgotPasswordState(
    val email: String = "",
    val isLoading: Boolean = false
)