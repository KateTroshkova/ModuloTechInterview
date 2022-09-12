package com.noveogroup.modulotechinterview.data.database.mapper

import com.noveogroup.modulotechinterview.data.database.entity.DeviceDB
import com.noveogroup.modulotechinterview.domain.common.Mapper
import com.noveogroup.modulotechinterview.domain.entity.device.Device
import com.noveogroup.modulotechinterview.domain.entity.device.Heater
import com.noveogroup.modulotechinterview.domain.entity.device.Light
import com.noveogroup.modulotechinterview.domain.entity.device.Shutter
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.entity.type.ProductType

internal object DeviceDBMapper : Mapper<Device, DeviceDB>(
    fromDtoMapper = {
        when (it.productType) {
            ProductType.LIGHT -> Light(
                id = it.id,
                deviceName = it.deviceName ?: "",
                intensity = it.intensity ?: 0,
                mode = it.mode ?: DeviceMode.OFF
            )
            ProductType.HEATER -> Heater(
                id = it.id,
                deviceName = it.deviceName ?: "",
                mode = it.mode ?: DeviceMode.OFF,
                temperature = it.temperature ?: 0.0f
            )
            ProductType.SHUTTER -> Shutter(
                id = it.id,
                deviceName = it.deviceName ?: "",
                position = it.position ?: 0
            )
        }
    },
    fromBusinessMapper = {
        DeviceDB(
            id = it.id,
            productType = it.productType,
            deviceName = it.deviceName,
            intensity = if (it is Light) it.intensity else 0,
            mode = when (it) {
                is Light -> it.mode
                is Heater -> it.mode
                else -> null
            },
            position = if (it is Shutter) it.position else 0,
            temperature = if (it is Heater) it.temperature else 0.0f
        )
    }
)