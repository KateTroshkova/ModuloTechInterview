package com.noveogroup.modulotechinterview.data.database.converter

import androidx.room.TypeConverter
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.entity.type.ProductType

internal object EnumConverter {

    @TypeConverter
    @JvmStatic
    fun stringToDeviceMode(string: String?): DeviceMode? =
        string.convertToEnum<DeviceMode>()

    @TypeConverter
    @JvmStatic
    fun deviceModeToString(enum: DeviceMode?): String? =
        enum.convertToString()

    @TypeConverter
    @JvmStatic
    fun stringToProductType(string: String?): ProductType? =
        string.convertToEnum<ProductType>()

    @TypeConverter
    @JvmStatic
    fun productTypeToString(enum: ProductType?): String? =
        enum.convertToString()

    private inline fun <reified T : Enum<T>> T?.convertToString(): String? = this?.name

    private inline fun <reified T : Enum<T>> String?.convertToEnum(): T? =
        this?.let { enumValueOf<T>(it) }
}
