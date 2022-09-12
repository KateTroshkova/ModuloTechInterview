package com.noveogroup.modulotechinterview.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "firstName") val firstName: String?,
    @Json(name = "lastName") val lastName: String?,
    @Json(name = "address") val address: AddressResponse?,
    @Json(name = "birthDate") val birthdate: Long?
)
