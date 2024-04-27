package edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation

import android.content.Context
import android.location.Address
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.common.util.GeocoderUtil
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil.showToast
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.model.Intolerance
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.use_cases.EditProfileInputValidationType
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.use_cases.ValidateEditProfileInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository,
    private val validateEditProfileInputUseCase: ValidateEditProfileInputUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _displayName = MutableStateFlow("")
    val displayName = _displayName.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    init {
        val currentUser = authRepository.getCurrentUser()

        _email.value = currentUser.email!!
        if (currentUser.displayName != null)
            _displayName.value = currentUser.displayName!!

        viewModelScope.launch {
            usersRepository.getUser(currentUser.uid).collectLatest { user ->
                _user.update { user!! }
            }
        }
    }

    fun updateDisplayName(newName: String) {
        _displayName.value = newName
    }

    fun updateCurrentCity(currentCity: String) {
        _user.value = _user.value.copy(currentCity = currentCity)
    }

    fun onAllergyClick(intolerance: Intolerance) {
        val intolerances = _user.value.intolerances.toMutableList()
        if (intolerances.contains(intolerance.label)) {
            intolerances.remove(intolerance.label)
        } else {
            intolerances.add(intolerance.label)
        }
        _user.value = _user.value.copy(intolerances = intolerances)
    }

    fun onSaveEdit(context: Context) {
        val validationResult =
            validateEditProfileInputUseCase(_displayName.value, _user.value.currentCity)

        if (validationResult != EditProfileInputValidationType.Valid) {
            showToast(validationResult.message!!, context)
            return
        }

        val address = GeocoderUtil.getAddressByName(_user.value.currentCity, context)
        if (!address.hasLatitude() || !address.hasLongitude()) {
            showToast("Address not found, try again", context)
            return
        }

        _user.value = addAddress(_user.value, address)

        viewModelScope.launch {
            _isLoading.value = true
            val authResult = authRepository.updateProfile(_displayName.value)
            if (!authResult) {
                showToast("Error on update auth user, try again.", context)
                _isLoading.value = false
                return@launch
            }

            val userResult = usersRepository.update(_user.value)
            if (!userResult) {
                showToast("Error on storing user, try again.", context)
                _isLoading.value = false
                return@launch
            }

            showToast("Profile saved successfully.", context)
            _isLoading.value = false
        }
    }

    private fun addAddress(user: User, address: Address): User {
        val location = address.getAddressLine(0)
        val lat = address.latitude
        val lng = address.longitude

        return user.copy(currentCity = location, latitude = lat, longitude = lng)
    }

    fun onLogoutClick(toLogin: () -> Unit) {
        if (!authRepository.signOut()) {
            Log.d("avb", "onLogoutClick: problem found on logout!")
            return
        }

        toLogin()
    }
}