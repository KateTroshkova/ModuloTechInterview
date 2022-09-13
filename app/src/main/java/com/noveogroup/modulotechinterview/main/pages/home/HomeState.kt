package com.noveogroup.modulotechinterview.main.pages.home

import com.noveogroup.modulotechinterview.domain.entity.device.Device
import com.noveogroup.modulotechinterview.domain.entity.device.Heater
import com.noveogroup.modulotechinterview.domain.entity.device.Light
import com.noveogroup.modulotechinterview.domain.entity.device.Shutter

data class HomeState(
    val devices: List<Device>,
    val isLightSelected: Boolean,
    val isHeaterSelected: Boolean,
    val isShutterSelected: Boolean
) : java.io.Serializable {

    val filteredDevices = devices.filter {
        when (it) {
            is Light -> isLightSelected
            is Shutter -> isShutterSelected
            is Heater -> isHeaterSelected
            else -> false
        }
    }
}
