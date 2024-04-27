package edu.uoc.avalldeperas.eatsafe.auth.register.presentation

import android.content.Context
import android.location.Address
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.auth.register.domain.model.RegisterInputValidationType
import edu.uoc.avalldeperas.eatsafe.auth.register.domain.use_cases.ValidateRegisterInputUseCase
import edu.uoc.avalldeperas.eatsafe.auth.register.presentation.state.RegisterState
import edu.uoc.avalldeperas.eatsafe.common.util.GeocoderUtil
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository,
    private val validateRegisterInputUseCase: ValidateRegisterInputUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    fun updateEmail(newEmail: String) {
        val username = newEmail.split("@")[0]
        _registerState.update { currentState ->
            currentState.copy(
                email = newEmail,
                username = username
            )
        }
    }

    fun updatePassword(newPassword: String) {
        _registerState.update { currentState -> currentState.copy(password = newPassword) }
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _registerState.update {
            currentState -> currentState.copy(confirmPassword = newConfirmPassword)
        }

    }

    fun updateCurrentCity(currentCity: String) {
        _registerState.update { currentState -> currentState.copy(currentCity = currentCity) }
    }

    fun onToggleVisualTransformationPassword() {
        _registerState.update {
            currentState -> currentState.copy(isPasswordShown = !currentState.isPasswordShown)
        }
    }

    fun onToggleVisualTransformationConfirmPasswd() {
        _registerState.update {
            currentState -> currentState.copy(isConfirmPasswordShown = !currentState.isConfirmPasswordShown)
        }
    }

    fun onRegisterClick(navigateToExplore: () -> Unit, context: Context) {
        val validationResult =
            validateRegisterInputUseCase(
                _registerState.value.email,
                _registerState.value.password,
                _registerState.value.confirmPassword,
                _registerState.value.currentCity
            )

        if (validationResult != RegisterInputValidationType.Valid) {
            showToast(validationResult.message!!, context)
            return
        }

        val address = GeocoderUtil.getAddressByName(_registerState.value.currentCity, context)
        if (!address.hasLatitude() || !address.hasLongitude()) {
            showToast("Address not found, try again", context)
            return
        }

        viewModelScope.launch {
            _registerState.update { currentState -> currentState.copy(isLoading = true) }

            val uid =
                authRepository.signUp(_registerState.value.email, _registerState.value.password)
            if (uid.isEmpty()) {
                showToast("Error on creating user, please try again", context)
                _registerState.update { currentState -> currentState.copy(isLoading = false) }
                return@launch
            }

            val user = buildUser(uid, address)
            val result = usersRepository.save(user)
            if (!result) {
                showToast("Error on storing user, please try again", context)
                _registerState.update { currentState -> currentState.copy(isLoading = false) }
                return@launch
            }
            navigateToExplore()
            _registerState.update { currentState -> currentState.copy(isLoading = false) }
        }
    }

    private fun buildUser(uid: String, address: Address): User {
        val location = address.getAddressLine(0)
        val lat = address.latitude
        val lng = address.longitude
        val geohash = GeoFireUtils.getGeoHashForLocation(GeoLocation(lat, lng))

        return User(
            uid = uid,
            email = _registerState.value.email,
            username = _registerState.value.username,
            currentCity = location,
            latitude = lat,
            longitude = lng,
            geohash = geohash
        )
    }
}