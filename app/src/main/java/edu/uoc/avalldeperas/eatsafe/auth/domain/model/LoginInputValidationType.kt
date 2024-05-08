package edu.uoc.avalldeperas.eatsafe.auth.domain.model

enum class LoginInputValidationType(val message: String?, val isValid: Boolean) {
    EmptyField("Empty fields left", false),
    InvalidEmail("No valid email", false),
    Valid(null, true)
}