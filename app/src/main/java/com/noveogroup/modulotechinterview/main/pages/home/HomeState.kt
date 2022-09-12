package com.noveogroup.modulotechinterview.main.pages.home

import com.noveogroup.modulotechinterview.domain.entity.device.Device

data class HomeState(
    val devices: List<Device>,
    val filteredDevices: List<Device>,
    val isLightSelected: Boolean,
    val isHeaterSelected: Boolean,
    val isShutterSelected: Boolean
) : java.io.Serializable
