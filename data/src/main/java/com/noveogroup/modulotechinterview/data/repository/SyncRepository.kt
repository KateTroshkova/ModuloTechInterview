package com.noveogroup.modulotechinterview.data.repository

import com.noveogroup.modulotechinterview.data.database.dao.DeviceDao
import com.noveogroup.modulotechinterview.data.database.dao.UserDao
import com.noveogroup.modulotechinterview.data.database.mapper.DeviceDBMapper
import com.noveogroup.modulotechinterview.data.database.mapper.UserDBMapper
import com.noveogroup.modulotechinterview.data.network.api.Storage42Api
import com.noveogroup.modulotechinterview.data.network.converter.DeviceConverter
import com.noveogroup.modulotechinterview.data.network.converter.UserConverter
import com.noveogroup.modulotechinterview.data.preferences.DataPreferences
import com.noveogroup.modulotechinterview.domain.api.SyncRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncRepository(
    private val api: Storage42Api,
    private val deviceDao: DeviceDao,
    private val userDao: UserDao,
    private val preferences: DataPreferences,
) : SyncRepositoryApi {

    override suspend fun syncData() {
        withContext(Dispatchers.IO) {
            val hasData = preferences.hasLocalData
            if (!hasData) {
                val response = api.loadAllData()
                val devices = response.devices.map { DeviceConverter.fromDto(it) }
                val user = UserConverter.fromDto(response.user)
                deviceDao.deleteAll()
                userDao.deleteAll()
                userDao.upsert(UserDBMapper.fromBusiness(user))
                deviceDao.upsert(devices.map { DeviceDBMapper.fromBusiness(it) })
                preferences.hasLocalData = true
            }
        }
    }
}