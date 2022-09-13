package com.noveogroup.modulotechinterview.domain.entity.user

data class User(
    val firstName: String,
    val lastName: String,
    val address: Address?,
    val birthdate: String
) : java.io.Serializable

