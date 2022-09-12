package com.noveogroup.modulotechinterview.data.repository

import com.noveogroup.modulotechinterview.data.database.dao.DeviceDao
import com.noveogroup.modulotechinterview.data.database.mapper.DeviceDBMapper
import com.noveogroup.modulotechinterview.domain.api.DeviceRepositoryApi
import com.noveogroup.modulotechinterview.domain.entity.device.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DeviceRepository(
    private val deviceDao: DeviceDao,
) : DeviceRepositoryApi {

    override suspend fun loadDevices(): List<Device> =
        withContext(Dispatchers.IO) {
            deviceDao.selectAll().map { DeviceDBMapper.fromDto(it) }
        }

    override suspend fun deleteDevice(device: Device) {
        withContext(Dispatchers.IO) {
            deviceDao.delete(DeviceDBMapper.fromBusiness(device))
        }
    }

    override suspend fun updateDevice(device: Device) {
        withContext(Dispatchers.IO) {
            deviceDao.update(DeviceDBMapper.fromBusiness(device))
        }
    }
}
