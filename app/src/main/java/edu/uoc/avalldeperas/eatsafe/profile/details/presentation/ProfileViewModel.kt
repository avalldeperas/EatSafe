package edu.uoc.avalldeperas.eatsafe.profile.details.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _displayName = MutableStateFlow("User")
    val displayName = _displayName.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    init {
        val currentUser = Firebase.auth.currentUser
        _email.value = currentUser?.email!!
        if (currentUser.displayName != null) _displayName.value = currentUser.displayName!!

        viewModelScope.launch {
            if (_user.value.currentCity.isEmpty()) {
                Log.d("avb", "We don't have have user... ${user.value}")
                _user.value = usersRepository.getUser(currentUser.uid)!!
            }
        }
    }

    fun onLogoutClick(toLogin: () -> Unit) {
        viewModelScope.launch {
            val result = authRepository.signOut()
            if (result) {
                toLogin()
            } else {
                Log.d("avb", "onLogoutClick: problem found on logout!")
            }
        }
    }
}