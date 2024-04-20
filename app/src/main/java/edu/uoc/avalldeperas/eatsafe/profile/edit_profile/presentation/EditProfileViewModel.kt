package edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation

import android.content.Context
import android.location.Address
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.common.util.GeocoderUtil
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.model.Intolerance
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.use_cases.EditProfileInputValidationType
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.use_cases.ValidateEditProfileInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    init {
        val currentUser = authRepository.getCurrentUser()

        _email.value = currentUser.email!!
        if (currentUser.displayName != null)
            _displayName.value = currentUser.displayName!!

        viewModelScope.launch {
            if (_user.value.currentCity.isEmpty()) {
                Log.d("avb", "We don't have have user... ${user.value}")
                val user = usersRepository.getUser(currentUser.uid)!!
                _user.value = user
                _user.value = _user.value.copy(uid = currentUser.uid)
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
        val indexOf = _user.value.intolerances.indexOf(intolerance)
        Log.d("avb", "onAllergyClick:indexOf = $indexOf")
        val userIntolerance = _user.value.intolerances[indexOf]
        Log.d("avb", "onAllergyClick:intoleranceFound = $userIntolerance")
    }

    fun onSaveEdit(context: Context) {
        val validationResult =
            validateEditProfileInputUseCase(_displayName.value, _user.value.currentCity)

        if (validationResult != EditProfileInputValidationType.Valid) {
            showToast(context, validationResult.message!!)
            return
        }

        val address = GeocoderUtil.getAddressByName(_user.value.currentCity, context)
        if (!address.hasLatitude() || !address.hasLongitude()) {
            showToast(context, "Address not found, try again")
            return
        }

        _user.value = addAddress(_user.value, address)

        viewModelScope.launch {
            val authResult = authRepository.updateProfile(_displayName.value)
            if (!authResult) {
                showToast(context, "Error on update auth user, try again.")
                return@launch
            }

            val userResult = usersRepository.update(_user.value)
            if (!userResult) {
                showToast(context, "Error on storing user, try again.")
                return@launch
            }

            showToast(context, "Profile saved successfully.")
        }
    }

    private fun addAddress(user: User, address: Address): User {
        val location = address.getAddressLine(0)
        val lat = address.latitude
        val lng = address.longitude

        return user.copy(currentCity = location, latitude = lat, longitude =  lng)
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}