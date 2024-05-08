package edu.uoc.avalldeperas.eatsafe.profile.domain.use_cases

enum class EditProfileInputValidationType(val message: String?, val isValid: Boolean) {
    Valid(null, true),
    EmptyField("Empty fields left", false),
    DisplayNameTooShort("Display name needs to be at least 3 chars long", false),
    DisplayNameTooLong("Display name needs to be at most 15 chars long", false),
    AddressTooShort("Address needs to be at least 6 chars long", false),
    InvalidAddress("Address must not contain invalid characters", false)
}