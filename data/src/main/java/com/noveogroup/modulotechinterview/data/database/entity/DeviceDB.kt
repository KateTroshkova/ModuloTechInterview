package com.noveogroup.modulotechinterview.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noveogroup.modulotechinterview.data.database.entity.DeviceDB.Companion.TABLE_NAME
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.entity.type.ProductType

@Entity(tableName = TABLE_NAME)
internal data class DeviceDB(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: String,
    @ColumnInfo(name = COLUMN_PRODUCT_TYPE) val productType: ProductType,
    @ColumnInfo(name = COLUMN_DEVICE_NAME) val deviceName: String?,
    @ColumnInfo(name = COLUMN_INTENSITY) val intensity: Int?,
    @ColumnInfo(name = COLUMN_MODE) val mode: DeviceMode?,
    @ColumnInfo(name = COLUMN_POSITION) val position: Int?,
    @ColumnInfo(name = COLUMN_TEMPERATURE) val temperature: Float?
) {

    companion object {
        const val TABLE_NAME = "devices"
        const val QUERY_SELECT_ALL = "select * from $TABLE_NAME"
        const val QUERY_DELETE_ALL = "delete from $TABLE_NAME"
        private const val COLUMN_ID = "id"
        private const val COLUMN_PRODUCT_TYPE = "product_type"
        private const val COLUMN_DEVICE_NAME = "device_name"
        private const val COLUMN_INTENSITY = "intensity"
        private const val COLUMN_MODE = "mode"
        private const val COLUMN_POSITION = "position"
        private const val COLUMN_TEMPERATURE = "temperature"
    }
}