package com.noveogroup.modulotechinterview.main.pages.device_shutter

import com.noveogroup.modulotechinterview.domain.entity.device.Shutter

data class ShutterState(
    val defaultShutter: Shutter,
    val position: Int
)
