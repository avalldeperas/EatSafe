package edu.uoc.avalldeperas.eatsafe.profile.presentation.edit_profile

import edu.uoc.avalldeperas.eatsafe.auth.domain.model.User

data class EditProfileState(
    val email: String = "",
    val displayName: String = "",
    val currentCity: String = "",
    val intolerances: MutableList<String> = mutableListOf(),
    val user: User = User(),
    val isLoading: Boolean = false
)
