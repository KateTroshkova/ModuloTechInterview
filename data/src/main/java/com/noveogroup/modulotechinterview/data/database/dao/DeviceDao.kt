package com.noveogroup.modulotechinterview.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.noveogroup.modulotechinterview.data.database.entity.DeviceDB

@Dao
abstract class DeviceDao : BaseDao<DeviceDB>() {

    @Query(DeviceDB.QUERY_SELECT_ALL)
    abstract suspend fun selectAll(): List<DeviceDB>

    @Query(DeviceDB.QUERY_DELETE_ALL)
    abstract suspend fun deleteAll()

}
