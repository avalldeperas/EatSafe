package edu.uoc.avalldeperas.eatsafe.auth.register.presentation

import android.content.Context
import android.location.Address
import android.widget.Toast
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
import edu.uoc.avalldeperas.eatsafe.common.util.GeocoderUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository,
    private val validateRegisterInputUseCase: ValidateRegisterInputUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _currentCity = MutableStateFlow("")
    val currentCity = _currentCity.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateConfirmPassword(newPassword: String) {
        _confirmPassword.value = newPassword
    }

    fun updateCurrentCity(currentCity: String) {
        _currentCity.value = currentCity
    }

    fun onRegisterClick(navigateToExplore: () -> Unit, context: Context) {
        val validationResult =
            validateRegisterInputUseCase(
                _email.value,
                _password.value,
                _confirmPassword.value,
                _currentCity.value
            )

        if (validationResult != RegisterInputValidationType.Valid) {
            showToast(context, validationResult.message!!)
            return
        }

        val address = GeocoderUtil.getAddressByName(_currentCity.value, context)
        if (!address.hasLatitude() || !address.hasLongitude()) {
            showToast(context, "Address not found, try again")
            return
        }

        viewModelScope.launch {
            val uid = authRepository.signUp(_email.value, _password.value)
            if (uid.isEmpty()) {
                showToast(context, "Error on saving user to auth, try again.")
                return@launch
            }

            val user = buildUser(uid, address)
            val result = usersRepository.save(user)
            if (!result) {
                showToast(context, "Error on saving to storage, see logs.")
                return@launch
            }
            navigateToExplore()
        }
    }

    private fun buildUser(uid: String, address: Address): User {
        val location = address.getAddressLine(0)
        val lat = address.latitude
        val lng = address.longitude
        val geoHash = GeoFireUtils.getGeoHashForLocation(GeoLocation(lat, lng))

        return User(uid, _email.value, location, lat, lng, geoHash)
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}