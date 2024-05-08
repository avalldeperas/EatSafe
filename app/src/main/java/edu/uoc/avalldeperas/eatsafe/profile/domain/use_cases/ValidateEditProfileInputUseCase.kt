package edu.uoc.avalldeperas.eatsafe.profile.domain.use_cases

import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEditProfileInputUseCase @Inject constructor() {

    operator fun invoke(
        displayName: String,
        currentCity: String
    ): EditProfileInputValidationType {

        if (currentCity.isEmpty()) {
            return EditProfileInputValidationType.EmptyField
        }

        if (displayName.isNotEmpty() && displayName.length < MIN_DISPLAY_NAME_LENGTH) {
            return EditProfileInputValidationType.DisplayNameTooShort
        }

        if (displayName.isNotEmpty() && displayName.length > MAX_DISPLAY_NAME_LENGTH) {
            return EditProfileInputValidationType.DisplayNameTooLong
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
        const val MIN_DISPLAY_NAME_LENGTH = 3
        const val MAX_DISPLAY_NAME_LENGTH = 15
        const val MIN_ADDRESS_LENGTH = 6
        val specialChars: Pattern = Pattern.compile("[^A-zÀ-ú0-9 ,]", Pattern.CASE_INSENSITIVE)
    }
}