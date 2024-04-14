package edu.uoc.avalldeperas.eatsafe.auth.register.domain.use_cases

import androidx.core.util.PatternsCompat
import edu.uoc.avalldeperas.eatsafe.auth.register.domain.model.RegisterInputValidationType
import javax.inject.Inject

class ValidateRegisterInputUseCase @Inject constructor() {

    operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
    ): RegisterInputValidationType {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return RegisterInputValidationType.EmptyField
        }

        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            return RegisterInputValidationType.InvalidEmail
        }

        if (password != confirmPassword) {
            return RegisterInputValidationType.PasswordsDoNotMatch
        }

        if (password.length < MIN_PASSWORD_LENGTH) {
            return RegisterInputValidationType.PasswordTooShort
        }

        return RegisterInputValidationType.Valid
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }
}