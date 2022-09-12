package com.noveogroup.modulotechinterview.domain.api

import com.noveogroup.modulotechinterview.domain.entity.user.User

interface UserRepositoryApi {
    suspend fun loadUser(): User
    suspend fun updateUser(oldUser: User, newUser: User)
}
