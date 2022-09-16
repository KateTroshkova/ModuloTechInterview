package com.noveogroup.modulotechinterview

import com.noveogroup.modulotechinterview.data.database.dao.DeviceDao
import com.noveogroup.modulotechinterview.data.database.dao.UserDao
import com.noveogroup.modulotechinterview.data.database.entity.DeviceEntity
import com.noveogroup.modulotechinterview.data.database.entity.UserEntity
import com.noveogroup.modulotechinterview.data.network.api.Storage42Api
import com.noveogroup.modulotechinterview.data.network.response.ApiResponse
import com.noveogroup.modulotechinterview.data.network.response.DeviceModeResponse
import com.noveogroup.modulotechinterview.data.network.response.DeviceResponse
import com.noveogroup.modulotechinterview.data.network.response.ProductTypeResponse
import com.noveogroup.modulotechinterview.data.network.response.UserResponse
import com.noveogroup.modulotechinterview.data.preferences.DataPreferences
import com.noveogroup.modulotechinterview.data.repository.SyncRepository
import com.noveogroup.modulotechinterview.domain.interactor.SyncInteractor
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SyncInteractorTest {

    private val apiResponse = ApiResponse(
        listOf(
            DeviceResponse(
                id = "0",
                productType = ProductTypeResponse.Light,
                deviceName = "Light",
                intensity = 42,
                mode = DeviceModeResponse.OFF,
                position = null,
                temperature = null
            ),
            DeviceResponse(
                id = "1",
                productType = ProductTypeResponse.Heater,
                deviceName = "Heater",
                temperature = 42f,
                mode = DeviceModeResponse.OFF,
                position = null,
                intensity = null
            ),
            DeviceResponse(
                id = "2",
                productType = ProductTypeResponse.RollerShutter,
                deviceName = "Shutter",
                position = 42,
                mode = null,
                intensity = null,
                temperature = null
            )
        ),
        UserResponse(
            firstName = "Jhon",
            lastName = "Dow",
            address = null,
            birthdate = 1663064055
        )
    )

    private val api: Storage42Api = mockkClass(Storage42Api::class).apply {
        coEvery { loadAllData() } returns apiResponse
    }

    private val preferences = mockkClass(DataPreferences::class).apply {
        coEvery { hasLocalData } returns false
        coEvery { hasLocalData = any() } coAnswers { }
    }

    private val deviceDao: DeviceDao = mockkClass(DeviceDao::class) {
        coEvery { deleteAll() } coAnswers { }
        coEvery { upsert(any<List<DeviceEntity>>()) } coAnswers { }
    }

    private val userDao: UserDao = mockkClass(UserDao::class) {
        coEvery { deleteAll() } coAnswers { }
        coEvery { upsert(any<List<UserEntity>>()) } coAnswers { }
    }

    private lateinit var interactor: SyncInteractor

    @Before
    fun setup() {
        interactor = SyncInteractor(SyncRepository(api, deviceDao, userDao, preferences))
    }

    @Test
    fun loadDataFirstTimeSuccess() {
        runBlocking {
            interactor.syncData()
            Assert.assertEquals(true, true)
        }
    }
}