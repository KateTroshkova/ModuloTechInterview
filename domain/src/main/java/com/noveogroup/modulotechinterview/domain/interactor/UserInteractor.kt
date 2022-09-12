package com.noveogroup.modulotechinterview.domain.interactor

import com.noveogroup.modulotechinterview.domain.api.UserRepositoryApi
import com.noveogroup.modulotechinterview.domain.entity.user.User

class UserInteractor(
    private val api: UserRepositoryApi
) {

    suspend fun loadUser(): User = api.loadUser()
    suspend fun updateUser(oldUser: User, newUser: User) = api.updateUser(oldUser, newUser)
}