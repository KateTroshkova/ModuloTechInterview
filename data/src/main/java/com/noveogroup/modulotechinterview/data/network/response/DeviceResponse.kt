package com.noveogroup.modulotechinterview.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceResponse(
    @Json(name = "id") val id: String,
    @Json(name = "productType") val productType: ProductTypeResponse,
    @Json(name = "deviceName") val deviceName: String?,
    @Json(name = "intensity") val intensity: Int?,
    @Json(name = "mode") val mode: DeviceModeResponse?,
    @Json(name = "position") val position: Int?,
    @Json(name = "temperature") val temperature: Float?
)
