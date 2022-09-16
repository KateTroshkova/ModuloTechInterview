package com.noveogroup.modulotechinterview.data.network.converter

import com.noveogroup.modulotechinterview.data.network.response.UserResponse
import com.noveogroup.modulotechinterview.domain.common.Mapper
import com.noveogroup.modulotechinterview.domain.entity.user.User
import java.util.*

object UserConverter : Mapper<User, UserResponse>(
    fromDtoMapper = {
        User(
            firstName = it.firstName ?: "",
            lastName = it.lastName ?: "",
            address = it.address?.let { address -> AddressConverter.fromDto(address) },
            birthdate = Date(it.birthdate ?: 0)
        )
    }
)
