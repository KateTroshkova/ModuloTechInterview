package com.noveogroup.modulotechinterview.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noveogroup.modulotechinterview.data.database.entity.AddressEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class AddressEntity(
    @ColumnInfo(name = COLUMN_CITY) val city: String?,
    @ColumnInfo(name = COLUMN_POSTAL_CODE) val postalCode: String?,
    @ColumnInfo(name = COLUMN_STREET) val street: String?,
    @ColumnInfo(name = COLUMN_STREET_CODE) val streetCode: String?,
    @ColumnInfo(name = COLUMN_COUNTRY) val country: String?
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0

    companion object {
        const val TABLE_NAME = "addresses"
        private const val COLUMN_ID = "address_id"
        private const val COLUMN_CITY = "city"
        private const val COLUMN_POSTAL_CODE = "postal_code"
        private const val COLUMN_STREET = "street"
        private const val COLUMN_STREET_CODE = "street_code"
        private const val COLUMN_COUNTRY = "country"
    }
}