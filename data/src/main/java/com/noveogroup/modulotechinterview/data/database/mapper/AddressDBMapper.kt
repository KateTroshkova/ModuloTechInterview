package com.noveogroup.modulotechinterview.data.database.mapper

import com.noveogroup.modulotechinterview.data.database.entity.AddressDB
import com.noveogroup.modulotechinterview.domain.common.Mapper
import com.noveogroup.modulotechinterview.domain.entity.user.Address

internal object AddressDBMapper : Mapper<Address, AddressDB>(
    fromDtoMapper = {
        Address(
            city = it.city ?: "",
            postalCode = it.postalCode ?: "",
            street = it.street ?: "",
            streetCode = it.streetCode ?: "",
            country = it.country ?: ""
        )
    },
    fromBusinessMapper = {
        AddressDB(
            city = it.city,
            postalCode = it.postalCode,
            street = it.street,
            streetCode = it.streetCode,
            country = it.country
        )
    }
)
