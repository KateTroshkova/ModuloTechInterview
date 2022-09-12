package com.noveogroup.modulotechinterview.domain.entity.device

import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.entity.type.ProductType

class Light(
    id: String,
    deviceName: String,
    val intensity: Int,
    val mode: DeviceMode
) : Device(id, deviceName, ProductType.LIGHT)
