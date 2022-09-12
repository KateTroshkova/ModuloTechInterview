package com.noveogroup.modulotechinterview.domain.interactor

import com.noveogroup.modulotechinterview.domain.api.DeviceRepositoryApi
import com.noveogroup.modulotechinterview.domain.entity.device.Device

class DeviceInteractor(
    private val deviceRepository: DeviceRepositoryApi
) {

    suspend fun loadDevices(): List<Device> = deviceRepository.loadDevices()
}