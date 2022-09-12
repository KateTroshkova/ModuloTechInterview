package com.noveogroup.modulotechinterview.domain.entity.device

import com.noveogroup.modulotechinterview.domain.entity.type.ProductType

class Shutter(
    id: String,
    deviceName: String,
    val position: Int
) : Device(id, deviceName, ProductType.SHUTTER)
