package edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation.state

import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User

data class EditProfileState(
    val email: String = "",
    val username: String = "",
    val displayName: String = "",
    val currentCity: String = "",
    val intolerances: MutableList<String> = mutableListOf(),
    val user: User = User(),
    val isLoading: Boolean = false
)
