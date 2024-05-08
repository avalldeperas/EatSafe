package edu.uoc.avalldeperas.eatsafe.auth.domain.use_cases

import androidx.core.util.PatternsCompat
import edu.uoc.avalldeperas.eatsafe.auth.domain.model.LoginInputValidationType
import javax.inject.Inject

class ValidateLoginInputUseCase @Inject constructor() {
    operator fun invoke(email: String, password: String): LoginInputValidationType {
        if (email.isEmpty() || password.isEmpty()) {
            return LoginInputValidationType.EmptyField
        }

        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            return LoginInputValidationType.InvalidEmail
        }

        return LoginInputValidationType.Valid
    }
}