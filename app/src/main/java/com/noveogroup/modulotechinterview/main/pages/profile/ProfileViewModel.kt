package com.noveogroup.modulotechinterview.main.pages.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.common.SingleLiveData
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.entity.user.Address
import com.noveogroup.modulotechinterview.domain.entity.user.User
import com.noveogroup.modulotechinterview.domain.interactor.UserInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class ProfileViewModel(
    private val userInteractor: UserInteractor
) : MvvmViewModel() {

    private var currentState: ProfileState = ProfileState(
        defaultUser = savedState[KEY_USER],
        firstName = savedState[KEY_FIRST_NAME] ?: "",
        lastName = savedState[KEY_LAST_NAME] ?: "",
        birthDate = savedState[KEY_BIRTHDATE] ?: "",
        city = savedState[KEY_CITY] ?: "",
        postalCode = savedState[KEY_POSTAL_CODE] ?: "",
        street = savedState[KEY_STREET] ?: "",
        streetCode = savedState[KEY_STREET_CODE] ?: "",
        country = savedState[KEY_COUNTRY] ?: "",
        firstNameError = false,
        lastNameError = false,
        birthdateError = false,
        cityError = false,
        postalCodeError = false,
        streetError = false,
        streetCodeError = false,
        countryError = false
    )

    private val _state: MutableLiveData<ProfileState> by lazy { MutableLiveData() }
    private val _errorEvent: SingleLiveData<Throwable> by lazy { SingleLiveData() }
    private val _loadingState: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    private val _informationEvent: SingleLiveData<Int> by lazy { SingleLiveData() }

    val state: LiveData<ProfileState> by lazy { _state }
    val errorEvent: LiveData<Throwable> by lazy { _errorEvent }
    val loadingState: LiveData<Boolean> by lazy { _loadingState }
    val informationEvent: LiveData<Int> by lazy { _informationEvent }

    init {
        updateState()
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loadingState.value = true
                val user = userInteractor.loadUser()
                currentState = currentState.copy(
                    defaultUser = user,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    birthDate = user.birthdate,
                    city = user.address?.city ?: "",
                    postalCode = user.address?.postalCode ?: "",
                    street = user.address?.street ?: "",
                    streetCode = user.address?.streetCode ?: "",
                    country = user.address?.country ?: ""
                )
                updateState()
                _loadingState.value = false
            } catch (e: Throwable) {
                _errorEvent.value = e
            }
        }
    }

    fun saveChanges() {
        val isFormattedDate = try {
            SimpleDateFormat(DATE_FORMAT).parse(currentState.birthDate)
            currentState.birthDate.length == DATE_LENGTH
        } catch (e: Throwable) {
            false
        }
        when {
            currentState.firstName.isEmpty() -> {
                currentState = currentState.copy(firstNameError = true)
                updateState()
            }
            currentState.lastName.isEmpty() -> {
                currentState = currentState.copy(lastNameError = true)
                updateState()
            }
            !isFormattedDate -> {
                currentState = currentState.copy(birthdateError = true)
                updateState()
            }
            currentState.city.isEmpty() -> {
                currentState = currentState.copy(cityError = true)
                updateState()
            }
            currentState.postalCode.isEmpty() || currentState.postalCode.any { !it.isDigit() } -> {
                currentState = currentState.copy(postalCodeError = true)
                updateState()
            }
            currentState.street.isEmpty() -> {
                currentState = currentState.copy(streetError = true)
                updateState()
            }
            currentState.streetCode.isEmpty() -> {
                currentState = currentState.copy(streetCodeError = true)
                updateState()
            }
            currentState.country.isEmpty() -> {
                currentState = currentState.copy(countryError = true)
                updateState()
            }
            else -> {
                update()
            }
        }
    }

    fun updateFirstName(firstName: String) {
        currentState = currentState.copy(firstName = firstName, firstNameError = false)
    }

    fun updateLastName(lastName: String) {
        currentState = currentState.copy(lastName = lastName, lastNameError = false)
    }

    fun updateBirthDate(birthdate: String) {
        currentState = currentState.copy(birthDate = birthdate, birthdateError = false)
    }

    fun updateCity(city: String) {
        currentState = currentState.copy(city = city, cityError = false)
    }

    fun updatePostalCode(postalCode: String) {
        currentState = currentState.copy(postalCode = postalCode, postalCodeError = false)
    }

    fun updateStreet(street: String) {
        currentState = currentState.copy(street = street, streetError = false)
    }

    fun updateStreetCode(streetCode: String) {
        currentState = currentState.copy(streetCode = streetCode, streetCodeError = false)
    }

    fun updateCountry(country: String) {
        currentState = currentState.copy(country = country, countryError = false)
    }

    private fun update() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loadingState.value = true
                val oldUser = currentState.defaultUser ?: return@launch
                userInteractor.updateUser(
                    oldUser,
                    User(
                        currentState.firstName,
                        currentState.lastName,
                        Address(
                            city = currentState.city,
                            postalCode = currentState.postalCode,
                            street = currentState.street,
                            streetCode = currentState.streetCode,
                            country = currentState.country
                        ),
                        currentState.birthDate
                    )
                )
                _loadingState.value = false
                _informationEvent.value = R.string.save_success
                updateState()
            } catch (e: Throwable) {
                _errorEvent.value = e
            }
        }
    }

    private fun updateState() {
        _state.value = currentState
        savedState[KEY_USER] = currentState.defaultUser
        savedState[KEY_FIRST_NAME] = currentState.firstName
        savedState[KEY_LAST_NAME] = currentState.lastName
        savedState[KEY_BIRTHDATE] = currentState.birthDate
        savedState[KEY_CITY] = currentState.city
        savedState[KEY_POSTAL_CODE] = currentState.postalCode
        savedState[KEY_STREET] = currentState.street
        savedState[KEY_STREET_CODE] = currentState.streetCode
        savedState[KEY_COUNTRY] = currentState.country
    }

    private companion object {
        private const val KEY_USER = "user"
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_LAST_NAME = "last_name"
        private const val KEY_BIRTHDATE = "birthdate"
        private const val KEY_CITY = "city"
        private const val KEY_POSTAL_CODE = "postal_code"
        private const val KEY_STREET = "street"
        private const val KEY_STREET_CODE = "street_code"
        private const val KEY_COUNTRY = "country"
        private const val DATE_FORMAT = "MM/dd/yyyy"
        private const val DATE_LENGTH = 10
    }
}