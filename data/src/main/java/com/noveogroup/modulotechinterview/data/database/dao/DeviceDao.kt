package com.noveogroup.modulotechinterview.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.noveogroup.modulotechinterview.data.database.entity.DeviceEntity

@Dao
abstract class DeviceDao : BaseDao<DeviceEntity>() {

    @Query(DeviceEntity.QUERY_SELECT_ALL)
    abstract suspend fun selectAll(): List<DeviceEntity>

    @Query(DeviceEntity.QUERY_DELETE_ALL)
    abstract suspend fun deleteAll()

}
