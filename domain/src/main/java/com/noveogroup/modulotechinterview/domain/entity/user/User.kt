package com.noveogroup.modulotechinterview.domain.entity.user

import java.util.Date

data class User(
    val firstName: String,
    val lastName: String,
    val address: Address?,
    val birthdate: Date
) : java.io.Serializable
