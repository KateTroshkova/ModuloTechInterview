package com.noveogroup.modulotechinterview.data.repository

import com.noveogroup.modulotechinterview.data.database.dao.UserDao
import com.noveogroup.modulotechinterview.data.database.mapper.UserDBMapper
import com.noveogroup.modulotechinterview.domain.api.UserRepositoryApi
import com.noveogroup.modulotechinterview.domain.entity.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UserRepository(
    private val userDao: UserDao
) : UserRepositoryApi {

    override suspend fun loadUser(): User = withContext(Dispatchers.IO) {
        userDao.selectAll().map { UserDBMapper.fromDto(it) }.first()
    }

    override suspend fun updateUser(oldUser: User, newUser: User) {
        withContext(Dispatchers.IO) {
            val entity = userDao.selectAll().firstOrNull {
                UserDBMapper.fromDto(it) == oldUser
            }
            val updatedEntity = entity?.let {
                UserDBMapper.fromBusiness(newUser).apply {
                    id = it.id
                }
            }
            updatedEntity?.let { userDao.update(it) }
        }
    }
}
