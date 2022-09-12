package com.noveogroup.modulotechinterview.main.pages.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    private val _state: MutableLiveData<ProfileState> by lazy { MutableLiveData() }

    fun loadUser() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val user = userInteractor.loadUser()
                _state.value = ProfileState(
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
            } catch (e: Throwable) {

            }
        }
    }

    fun saveChanges() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val state = _state.value ?: return@launch
                val oldUser = state.defaultUser
                userInteractor.updateUser(
                    oldUser,
                    User(
                        state.firstName,
                        state.lastName,
                        Address(
                            city = state.city,
                            postalCode = state.postalCode,
                            street = state.street,
                            streetCode = state.streetCode,
                            country = state.country
                        ),
                        state.birthDate
                    )
                )
            } catch (e: Throwable) {

            }
        }
    }

    fun updateFirstName(firstName: String) {
        if (firstName.isNotEmpty()) {
            _state.value = _state.value?.copy(firstName = firstName)
        }
    }

    fun updateLastName(lastName: String) {
        if (lastName.isNotEmpty()) {
            _state.value = _state.value?.copy(firstName = lastName)
        }
    }

    fun updateBirthDate(birthdate: String) {
        val format = SimpleDateFormat("MM/dd/yyyy")
        try {
            val date = format.parse(birthdate)
            _state.value = _state.value?.copy(birthDate = birthdate)
        } catch (e: Throwable) {

        }
    }

    fun updateCity(city: String) {
        if (city.isNotEmpty()) {
            _state.value = _state.value?.copy(city = city)
        }
    }

    fun updatePostalCode(postalCode: String) {
        if (postalCode.isNotEmpty()) {
            _state.value = _state.value?.copy(postalCode = postalCode)
        }
    }

    fun updateStreet(street: String) {
        if (street.isNotEmpty()) {
            _state.value = _state.value?.copy(street = street)
        }
    }

    fun updateStreetCode(streetCode: String) {
        if (streetCode.isNotEmpty()) {
            _state.value = _state.value?.copy(streetCode = streetCode)
        }
    }

    fun updateCountry(country: String) {
        if (country.isNotEmpty()) {
            _state.value = _state.value?.copy(country = country)
        }
    }
}