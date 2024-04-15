package edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.model.Intolerance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _displayName = MutableStateFlow("")
    val displayName = _displayName.asStateFlow()

    private val _intolerances =  MutableStateFlow(emptyList<Intolerance>())
    val intolerances = _intolerances.asStateFlow()

    init {
        val currentUser = Firebase.auth.currentUser
        _email.value = currentUser?.email!!
        if (currentUser.displayName != null)
            _displayName.value = currentUser.displayName!!

        _intolerances.value = Intolerance().intolerances()
    }

    fun updateDisplayName(newName: String) {
        _displayName.value = newName
    }

    fun onAllergyClick(intolerance: Intolerance) {
        val indexOf = _intolerances.value.indexOf(intolerance)
        Log.d("avb", "indexOf = $indexOf")
    }

    fun onSaveEdit() {
        viewModelScope.launch {
            val result = authRepository.updateProfile(_displayName.value)
            if (result) {
                Log.d("avb", "saved successfully!")
            } else {
                Log.d("avb", "couldn't save profile... check error!")
            }
        }
    }
}