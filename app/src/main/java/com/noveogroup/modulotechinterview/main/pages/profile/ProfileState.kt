package com.noveogroup.modulotechinterview.main.pages.profile

import com.noveogroup.modulotechinterview.domain.entity.user.User

data class ProfileState(
    val defaultUser: User,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val city: String,
    val postalCode: String,
    val street: String,
    val streetCode: String,
    val country: String
)
