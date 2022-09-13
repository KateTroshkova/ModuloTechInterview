package com.noveogroup.modulotechinterview.main.pages.device_light

import com.noveogroup.modulotechinterview.domain.entity.device.Light
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode

data class LightState(
    val defaultLight: Light?,
    val mode: DeviceMode,
    val intensity: Int
)
