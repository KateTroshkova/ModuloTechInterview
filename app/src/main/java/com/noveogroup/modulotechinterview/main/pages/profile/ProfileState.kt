package com.noveogroup.modulotechinterview.main.pages.profile

import com.noveogroup.modulotechinterview.domain.entity.user.User

data class ProfileState(
    val defaultUser: User?,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val city: String,
    val postalCode: String,
    val street: String,
    val streetCode: String,
    val country: String,
    val firstNameError: Boolean,
    val lastNameError: Boolean,
    val birthdateError: Boolean,
    val cityError: Boolean,
    val postalCodeError: Boolean,
    val streetError: Boolean,
    val streetCodeError: Boolean,
    val countryError: Boolean
)
