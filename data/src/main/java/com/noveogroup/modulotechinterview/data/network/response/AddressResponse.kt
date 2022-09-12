package com.noveogroup.modulotechinterview.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressResponse(
    @Json(name = "city") val city: String?,
    @Json(name = "postalCode") val postalCode: Int?,
    @Json(name = "street") val street: String?,
    @Json(name = "streetCode") val streetCode: String?,
    @Json(name = "country") val country: String?
)
