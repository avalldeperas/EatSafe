package edu.uoc.avalldeperas.eatsafe.profile.presentation.edit_profile

import android.content.Context
import android.location.Address
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.auth.domain.model.User
import edu.uoc.avalldeperas.eatsafe.common.util.GeocoderUtil
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil.showToast
import edu.uoc.avalldeperas.eatsafe.profile.domain.model.Intolerance
import edu.uoc.avalldeperas.eatsafe.profile.domain.use_cases.EditProfileInputValidationType
import edu.uoc.avalldeperas.eatsafe.profile.domain.use_cases.ValidateEditProfileInputUseCase
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

    private val _uiState = MutableStateFlow(EditProfileState())
    val uiState = _uiState.asStateFlow()

    init {
        val currentUser = authRepository.getCurrentUser()
        _uiState.update { state -> state.copy(email = currentUser.email) }

        if (currentUser.displayName != null) {
            _uiState.update { state -> state.copy(displayName = currentUser.displayName!!) }
        }

        viewModelScope.launch {
            usersRepository.getUser(currentUser.uid).collectLatest { user ->
                _uiState.update { state ->
                    state.copy(
                        user = user!!,
                        currentCity = user.currentCity,
                        email = user.email,
                        intolerances = user.intolerances
                    )
                }
            }
        }
    }

    fun updateDisplayName(newName: String) {
        _uiState.update { state -> state.copy(displayName = newName) }
    }

    fun updateCurrentCity(currentCity: String) {
        _uiState.update { state -> state.copy(currentCity = currentCity) }
    }

    fun onAllergyClick(intolerance: Intolerance) {
        val intolerances = _uiState.value.intolerances.toMutableList()
        if (intolerances.contains(intolerance.label)) {
            intolerances.remove(intolerance.label)
        } else {
            intolerances.add(intolerance.label)
        }
        _uiState.update { state -> state.copy(intolerances = intolerances) }
    }

    fun onSaveEdit(context: Context) {
        val validationResult =
            validateEditProfileInputUseCase(_uiState.value.displayName, _uiState.value.currentCity)

        if (validationResult != EditProfileInputValidationType.Valid) {
            showToast(validationResult.message!!, context)
            return
        }

        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            val address = GeocoderUtil.getAddressByName(_uiState.value.currentCity, context)
            if (!address.hasLatitude() || !address.hasLongitude()) {
                showToast("Address not found, try again", context)
                return@launch
            }

            if (hasDisplayNameChanged()) {
                val authResult = authRepository.updateProfile(_uiState.value.displayName)
                if (!authResult) {
                    showToast("Error on update auth user, try again.", context)
                    _uiState.update { state -> state.copy(isLoading = true) }
                    return@launch
                }
            }

            if (hasCurrentCityChanged() || hasIntolerancesChanged()) {
                val userResult = usersRepository.update(buildUser(address))
                if (!userResult) {
                    showToast("Error on storing user, try again.", context)
                    _uiState.update { state -> state.copy(isLoading = false) }
                    return@launch
                }
            }

            showToast("Profile saved successfully.", context)
            _uiState.update { state -> state.copy(isLoading = false) }
        }
    }

    fun onLogoutClick(toLogin: () -> Unit) {
        if (!authRepository.signOut()) {
            Log.d("avb", "onLogoutClick: problem found on logout!")
            return
        }

        toLogin()
    }

    private fun hasCurrentCityChanged(): Boolean {
        return _uiState.value.currentCity != _uiState.value.user.currentCity
    }

    private fun hasDisplayNameChanged(): Boolean {
        return _uiState.value.displayName != _uiState.value.user.displayName
    }

    private fun hasIntolerancesChanged(): Boolean {
        return _uiState.value.intolerances != _uiState.value.user.intolerances
    }

    private fun buildUser(address: Address): User {
        val state = _uiState.value
        val location = address.getAddressLine(0)
        val lat = address.latitude
        val lng = address.longitude

        return state.user.copy(
            currentCity = location,
            latitude = lat,
            longitude = lng,
            intolerances = state.intolerances
        )
    }
}