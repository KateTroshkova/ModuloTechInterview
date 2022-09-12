package com.noveogroup.modulotechinterview.data.network.converter

import com.noveogroup.modulotechinterview.data.network.response.AddressResponse
import com.noveogroup.modulotechinterview.domain.common.Mapper
import com.noveogroup.modulotechinterview.domain.entity.user.Address

object AddressConverter : Mapper<Address, AddressResponse>(
    fromDtoMapper = {
        Address(
            city = it.city ?: "",
            postalCode = it.postalCode.toString(),
            street = it.street ?: "",
            streetCode = it.streetCode ?: "",
            country = it.country ?: ""
        )
    }
)
