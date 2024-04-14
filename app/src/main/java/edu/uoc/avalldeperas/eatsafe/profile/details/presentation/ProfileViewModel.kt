package edu.uoc.avalldeperas.eatsafe.profile.details.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    init {
        _email.value = Firebase.auth.currentUser?.email!!
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