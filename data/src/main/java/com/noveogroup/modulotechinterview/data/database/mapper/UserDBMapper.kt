package com.noveogroup.modulotechinterview.data.database.mapper

import com.noveogroup.modulotechinterview.data.database.entity.UserEntity
import com.noveogroup.modulotechinterview.domain.common.Mapper
import com.noveogroup.modulotechinterview.domain.entity.user.User

internal object UserDBMapper : Mapper<User, UserEntity>(
    fromDtoMapper = {
        User(
            firstName = it.firstName ?: "",
            lastName = it.lastName ?: "",
            address = it.address?.let { address -> AddressDBMapper.fromDto(address) },
            birthdate = it.birthdate ?: ""
        )
    },
    fromBusinessMapper = {
        UserEntity(
            firstName = it.firstName,
            lastName = it.lastName,
            address = it.address?.let { address -> AddressDBMapper.fromBusiness(address) },
            birthdate = it.birthdate
        )
    }
)
