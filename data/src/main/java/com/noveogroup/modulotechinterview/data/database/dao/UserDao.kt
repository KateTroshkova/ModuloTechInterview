package com.noveogroup.modulotechinterview.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.noveogroup.modulotechinterview.data.database.entity.UserEntity

@Dao
abstract class UserDao : BaseDao<UserEntity>() {

    @Query(UserEntity.QUERY_SELECT_ALL)
    abstract suspend fun selectAll(): List<UserEntity>

    @Query(UserEntity.QUERY_DELETE_ALL)
    abstract suspend fun deleteAll()

}
