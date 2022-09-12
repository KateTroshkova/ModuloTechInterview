package com.noveogroup.modulotechinterview.domain.entity.device

import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.entity.type.ProductType

class Heater(
    id: String,
    deviceName: String,
    val mode: DeviceMode,
    val temperature: Float
) : Device(id, deviceName, ProductType.HEATER)
