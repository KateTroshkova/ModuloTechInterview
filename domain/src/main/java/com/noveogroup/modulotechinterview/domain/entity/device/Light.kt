package com.noveogroup.modulotechinterview.domain.entity.device

import com.noveogroup.modulotechinterview.domain.entity.type.LightMode
import com.noveogroup.modulotechinterview.domain.entity.type.ProductType

class Light(
    id: String,
    deviceName: String,
    intensity: Int,
    mode: LightMode
) : Device(id, deviceName, ProductType.LIGHT)
