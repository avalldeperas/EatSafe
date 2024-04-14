package edu.uoc.avalldeperas.eatsafe.auth.register.domain.model

enum class RegisterInputValidationType(val message: String?, val isValid: Boolean) {
    EmptyField("Empty fields left", false),
    InvalidEmail("No valid email", false),
    PasswordsDoNotMatch("Password do not match", false),
    PasswordTooShort("Password needs to be 6 chars long", false),
    Valid(null, true)
}