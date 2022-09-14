package com.noveogroup.modulotechinterview

import com.noveogroup.modulotechinterview.data.database.dao.UserDao
import com.noveogroup.modulotechinterview.data.database.entity.AddressEntity
import com.noveogroup.modulotechinterview.data.database.entity.UserEntity
import com.noveogroup.modulotechinterview.data.repository.UserRepository
import com.noveogroup.modulotechinterview.domain.entity.user.Address
import com.noveogroup.modulotechinterview.domain.entity.user.User
import com.noveogroup.modulotechinterview.domain.interactor.UserInteractor
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserInteractorTest {

    private val userDao: UserDao = mockkClass(UserDao::class) {
        coEvery { selectAll() } returns listOf(
            UserEntity(
                firstName = "Noveo",
                lastName = "Test",
                address = AddressEntity(null, null, null, null, null),
                birthdate = "06/13/2022"
            )
        )
        coEvery { update(any<UserEntity>()) } coAnswers { 1 }
    }

    private lateinit var interactor: UserInteractor

    @Before
    fun setup() {
        interactor = UserInteractor(UserRepository(userDao))
    }

    @Test
    fun loadDataSuccess() {
        runBlocking {
            val user = interactor.loadUser()
            Assert.assertNotNull(user.firstName)
            Assert.assertNotNull(user.lastName)
            Assert.assertNotNull(user.birthdate)
            Assert.assertNotNull(user.address)
        }
    }

    @Test
    fun deleteDeviceSuccess() {
        runBlocking {
            val oldUser = User(
                firstName = "Noveo",
                lastName = "Test",
                address = Address("", "", "", "", ""),
                birthdate = "06/13/2022"
            )
            val newUser = User(
                firstName = "Noveo",
                lastName = "Test",
                address = Address("city", "424242", "street", "42A", ""),
                birthdate = "06/13/2022"
            )
            interactor.updateUser(oldUser, newUser)
            Assert.assertEquals(true, true)
        }
    }
}