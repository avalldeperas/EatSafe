package edu.uoc.avalldeperas.eatsafe.profile.details.presentation.state

import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review

data class ProfileState(
    val user: User = User(),
    val reviews: List<Review> = mutableListOf(),
    val isLoading: Boolean = false
)
