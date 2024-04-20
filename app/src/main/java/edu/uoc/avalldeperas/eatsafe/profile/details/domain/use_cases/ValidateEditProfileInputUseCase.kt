package edu.uoc.avalldeperas.eatsafe.profile.details.domain.use_cases

import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEditProfileInputUseCase @Inject constructor() {

    operator fun invoke(
        displayName: String,
        currentCity: String
    ): EditProfileInputValidationType {

        if (displayName.isEmpty() || currentCity.isEmpty()) {
            return EditProfileInputValidationType.EmptyField
        }

        if (displayName.length < MIN_DISPLAY_LENGTH) {
            return EditProfileInputValidationType.DisplayNameTooShort
        }

        if (currentCity.length < MIN_ADDRESS_LENGTH) {
            return EditProfileInputValidationType.AddressTooShort
        }

        if (specialChars.matcher(currentCity).find()) {
            return EditProfileInputValidationType.InvalidAddress
        }

        return EditProfileInputValidationType.Valid
    }

    companion object {
        const val MIN_DISPLAY_LENGTH = 3
        const val MIN_ADDRESS_LENGTH = 6
        val specialChars: Pattern = Pattern.compile("[^A-zÀ-ú0-9 ,]", Pattern.CASE_INSENSITIVE)
    }
}