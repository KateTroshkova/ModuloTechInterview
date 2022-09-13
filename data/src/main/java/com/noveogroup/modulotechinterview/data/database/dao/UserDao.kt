package com.noveogroup.modulotechinterview.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.noveogroup.modulotechinterview.data.database.entity.UserDB

@Dao
abstract class UserDao : BaseDao<UserDB>() {

    @Query(UserDB.QUERY_SELECT_ALL)
    abstract suspend fun selectAll(): List<UserDB>

    @Query(UserDB.QUERY_DELETE_ALL)
    abstract suspend fun deleteAll()

}
