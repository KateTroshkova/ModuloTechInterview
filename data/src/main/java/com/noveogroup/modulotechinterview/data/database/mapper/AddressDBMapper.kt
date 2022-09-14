package com.noveogroup.modulotechinterview.data.database.mapper

import com.noveogroup.modulotechinterview.data.database.entity.AddressEntity
import com.noveogroup.modulotechinterview.domain.common.Mapper
import com.noveogroup.modulotechinterview.domain.entity.user.Address

internal object AddressDBMapper : Mapper<Address, AddressEntity>(
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
        AddressEntity(
            city = it.city,
            postalCode = it.postalCode,
            street = it.street,
            streetCode = it.streetCode,
            country = it.country
        )
    }
)
