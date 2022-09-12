package com.noveogroup.modulotechinterview.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "devices") val devices: List<DeviceResponse>,
    @Json(name = "user") val user: UserResponse
)