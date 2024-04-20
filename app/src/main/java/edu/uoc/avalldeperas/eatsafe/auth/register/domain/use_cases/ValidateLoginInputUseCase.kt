package edu.uoc.avalldeperas.eatsafe.auth.register.domain.use_cases

import androidx.core.util.PatternsCompat
import edu.uoc.avalldeperas.eatsafe.auth.register.domain.model.RegisterInputValidationType
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateRegisterInputUseCase @Inject constructor() {

    operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
        currentCity: String
    ): RegisterInputValidationType {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || currentCity.isEmpty()) {
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

        if (currentCity.length < MIN_ADDRESS_LENGTH) {
            return RegisterInputValidationType.AddressTooShort
        }

        if (specialChars.matcher(currentCity).find()) {
            return RegisterInputValidationType.InvalidAddress
        }

        return RegisterInputValidationType.Valid
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
        const val MIN_ADDRESS_LENGTH = 6
        val specialChars: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
    }
}