package edu.uoc.avalldeperas.eatsafe.profile.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository,
    private val reviewsRepository: ReviewsRepository,
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    init {
        val currentUser = authRepository.getCurrentUser()
        val displayName = currentUser.displayName ?: "User"

        viewModelScope.launch {
            _profileState.update { state -> state.copy(isLoading = true) }
            launch {
                usersRepository.getUser(currentUser.uid).collectLatest { user ->
                    _profileState.update { state ->
                        state.copy(user = user!!.copy(displayName = displayName))
                    }
                }
            }
            reviewsRepository.getReviewsByUser(currentUser.uid).collectLatest { reviews ->
                _profileState.update { state ->
                    state.copy(
                        reviews = reviews.sortedWith(compareByDescending { it.date }),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getDisplayName(): String {
        val user = _profileState.value.user

        return user.displayName?.ifEmpty { user.username }?.ifEmpty { "User" }!!
    }
}