package com.noveogroup.modulotechinterview.data.network.converter

import com.noveogroup.modulotechinterview.data.network.response.DeviceModeResponse
import com.noveogroup.modulotechinterview.domain.common.Mapper
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode

object DeviceModeConverter : Mapper<DeviceMode, DeviceModeResponse>(
    fromDtoMapper = {
        when (it) {
            DeviceModeResponse.ON -> DeviceMode.ON
            DeviceModeResponse.OFF -> DeviceMode.OFF
        }
    }
)