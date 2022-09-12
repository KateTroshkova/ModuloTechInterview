package com.noveogroup.modulotechinterview.data.network.converter

import com.noveogroup.modulotechinterview.data.network.response.DeviceModeResponse
import com.noveogroup.modulotechinterview.data.network.response.DeviceResponse
import com.noveogroup.modulotechinterview.data.network.response.ProductTypeResponse
import com.noveogroup.modulotechinterview.domain.common.Mapper
import com.noveogroup.modulotechinterview.domain.entity.device.Device
import com.noveogroup.modulotechinterview.domain.entity.device.Heater
import com.noveogroup.modulotechinterview.domain.entity.device.Light
import com.noveogroup.modulotechinterview.domain.entity.device.Shutter

class DeviceConverter : Mapper<Device, DeviceResponse>(
    fromDtoMapper = {
        when (it.productType) {
            ProductTypeResponse.Light -> Light(
                id = it.id,
                deviceName = it.deviceName ?: "",
                intensity = it.intensity ?: 0,
                mode = DeviceModeConverter.fromDto(it.mode ?: DeviceModeResponse.OFF)
            )
            ProductTypeResponse.Heater -> Heater(
                id = it.id,
                deviceName = it.deviceName ?: "",
                mode = DeviceModeConverter.fromDto(it.mode ?: DeviceModeResponse.OFF),
                temperature = it.temperature ?: 0.0f
            )
            ProductTypeResponse.RollerShutter -> Shutter(
                id = it.id,
                deviceName = it.deviceName ?: "",
                position = it.position ?: 0
            )
        }
    }
)