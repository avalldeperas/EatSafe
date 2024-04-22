package edu.uoc.avalldeperas.eatsafe.profile.details.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()

            _user.update { usersRepository.getUser(currentUser.uid)!! }

            if (currentUser.displayName != null) {
                _user.value.displayName = currentUser.displayName!!
            }
        }
    }

    fun getDisplayName(): String {
        return _user.value.displayName.ifEmpty { _user.value.username }
    }

    fun onLogoutClick(toLogin: () -> Unit) {
        if (!authRepository.signOut()) {
            Log.d("avb", "onLogoutClick: problem found on logout!")
            return
        }

        toLogin()
    }
}