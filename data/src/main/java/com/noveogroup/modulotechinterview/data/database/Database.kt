package com.noveogroup.modulotechinterview.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noveogroup.modulotechinterview.data.database.converter.EnumConverter
import com.noveogroup.modulotechinterview.data.database.dao.DeviceDao
import com.noveogroup.modulotechinterview.data.database.dao.UserDao
import com.noveogroup.modulotechinterview.data.database.entity.AddressDB
import com.noveogroup.modulotechinterview.data.database.entity.DeviceDB
import com.noveogroup.modulotechinterview.data.database.entity.UserDB

@Database(
    version = 1,
    entities = [
        AddressDB::class,
        DeviceDB::class,
        UserDB::class,
    ]
)
@TypeConverters(
    EnumConverter::class,
)
internal abstract class Database : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao
    abstract fun userDao(): UserDao
}
