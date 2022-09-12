package com.noveogroup.modulotechinterview.data.database

import android.content.Context
import androidx.room.Room
import com.noveogroup.modulotechinterview.data.database.dao.DeviceDao
import com.noveogroup.modulotechinterview.data.database.dao.UserDao

internal class DatabaseProvider(context: Context) {
    private val database = Room
        .databaseBuilder(
            context,
            Database::class.java,
            "modulotech_db.db"
        )
        .build()

    val deviceDao: DeviceDao
        get() = database.deviceDao()
    val userDao: UserDao
        get() = database.userDao()
}
