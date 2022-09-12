package com.noveogroup.modulotechinterview.main.pages.device_heater

import com.noveogroup.modulotechinterview.domain.entity.device.Heater
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode

data class HeaterState(
    val defaultHeater: Heater,
    val mode: DeviceMode,
    val temperature: Float
)
