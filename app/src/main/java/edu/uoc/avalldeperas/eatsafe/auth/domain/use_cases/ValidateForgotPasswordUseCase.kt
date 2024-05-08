package edu.uoc.avalldeperas.eatsafe.auth.domain.use_cases

import androidx.core.util.PatternsCompat
import edu.uoc.avalldeperas.eatsafe.auth.domain.model.ForgotPasswordInputValidationType
import javax.inject.Inject

class ValidateForgotPasswordUseCase @Inject constructor() {
    operator fun invoke(email: String): ForgotPasswordInputValidationType {
        if (email.isEmpty()) {
            return ForgotPasswordInputValidationType.EmptyField
        }

        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            return ForgotPasswordInputValidationType.InvalidEmail
        }

        return ForgotPasswordInputValidationType.Valid
    }
}