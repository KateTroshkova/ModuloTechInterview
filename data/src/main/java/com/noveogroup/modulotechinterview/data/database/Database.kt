package com.noveogroup.modulotechinterview.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noveogroup.modulotechinterview.data.database.converter.EnumConverter
import com.noveogroup.modulotechinterview.data.database.dao.DeviceDao
import com.noveogroup.modulotechinterview.data.database.dao.UserDao
import com.noveogroup.modulotechinterview.data.database.entity.AddressEntity
import com.noveogroup.modulotechinterview.data.database.entity.DeviceEntity
import com.noveogroup.modulotechinterview.data.database.entity.UserEntity

@Database(
    version = 1,
    entities = [
        AddressEntity::class,
        DeviceEntity::class,
        UserEntity::class,
    ]
)
@TypeConverters(
    EnumConverter::class,
)
abstract class Database : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao
    abstract fun userDao(): UserDao
}
