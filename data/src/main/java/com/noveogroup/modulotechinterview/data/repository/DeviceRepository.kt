package com.noveogroup.modulotechinterview.data.repository

import com.noveogroup.modulotechinterview.data.network.api.Storage42Api
import com.noveogroup.modulotechinterview.data.network.converter.DeviceConverter
import com.noveogroup.modulotechinterview.domain.api.DeviceRepositoryApi
import com.noveogroup.modulotechinterview.domain.entity.device.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceRepository(
    private val api: Storage42Api
) : DeviceRepositoryApi {
    override suspend fun loadDevices(): List<Device> =
        withContext(Dispatchers.IO) {
            val response = api.loadAllData()
            response.devices
                .map { DeviceConverter.fromDto(it) }
        }

    override suspend fun deleteDevice() {
        TODO("Not yet implemented")
    }
}