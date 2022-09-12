package com.noveogroup.modulotechinterview.data.database.mapper

import com.noveogroup.modulotechinterview.data.database.entity.UserDB
import com.noveogroup.modulotechinterview.domain.common.Mapper
import com.noveogroup.modulotechinterview.domain.entity.user.User

internal object UserDBMapper : Mapper<User, UserDB>(
    fromDtoMapper = {
        User(
            firstName = it.firstName ?: "",
            lastName = it.lastName ?: "",
            address = it.address?.let { address -> AddressDBMapper.fromDto(address) },
            birthdate = it.birthdate ?: ""
        )
    },
    fromBusinessMapper = {
        UserDB(
            firstName = it.firstName,
            lastName = it.lastName,
            address = it.address?.let { address -> AddressDBMapper.fromBusiness(address) },
            birthdate = it.birthdate
        )
    }
)
