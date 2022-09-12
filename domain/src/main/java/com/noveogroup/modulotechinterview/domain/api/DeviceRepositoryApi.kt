package com.noveogroup.modulotechinterview.domain.api

import com.noveogroup.modulotechinterview.domain.entity.device.Device

interface DeviceRepositoryApi {

    suspend fun loadDevices(): List<Device>

    suspend fun deleteDevice()
}
