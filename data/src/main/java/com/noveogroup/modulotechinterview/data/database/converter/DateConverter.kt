package com.noveogroup.modulotechinterview.data.database.converter

import androidx.room.TypeConverter
import java.util.Date

internal object DateConverter {

    @TypeConverter
    @JvmStatic
    fun dateToLong(date: Date?): Long? = date?.time

    @TypeConverter
    @JvmStatic
    fun longToDate(millis: Long?): Date? = millis?.let { Date(it) }
}