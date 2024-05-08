package edu.uoc.avalldeperas.eatsafe.auth.presentation.forgot_password

data class ForgotPasswordState(
    val email: String = "",
    val isLoading: Boolean = false
)