package edu.uoc.avalldeperas.eatsafe.profile.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepository
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
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
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _reviews = MutableStateFlow<List<Review>>(mutableListOf())
    val reviews = _reviews.asStateFlow()

    init {
        val currentUser = authRepository.getCurrentUser()

        viewModelScope.launch {
            launch {
                usersRepository.getUser(currentUser.uid).collectLatest { user ->
                    _user.update { user!! }
                    _user.value = _user.value.copy(displayName = currentUser.displayName ?: "User")
                }
            }
            reviewsRepository.getReviewsByUser(currentUser.uid).collectLatest { reviews ->
                _reviews.update { reviews }
            }
        }
    }

    fun getDisplayName(): String {
        return _user.value.displayName?.ifEmpty { _user.value.username }?.ifEmpty { "User" }!!
    }
}