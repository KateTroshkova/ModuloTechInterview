package com.noveogroup.modulotechinterview.domain.entity.user

import java.time.ZonedDateTime

data class User(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val birthdate: ZonedDateTime
)