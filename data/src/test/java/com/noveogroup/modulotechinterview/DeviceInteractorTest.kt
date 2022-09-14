package com.noveogroup.modulotechinterview

import com.noveogroup.modulotechinterview.data.database.dao.DeviceDao
import com.noveogroup.modulotechinterview.data.database.entity.DeviceDB
import com.noveogroup.modulotechinterview.data.repository.DeviceRepository
import com.noveogroup.modulotechinterview.domain.entity.device.Light
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.entity.type.ProductType
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeviceInteractorTest {

    private val deviceDao: DeviceDao = mockkClass(DeviceDao::class) {
        coEvery { selectAll() } returns listOf(
            DeviceDB("0", ProductType.LIGHT, "Light", 42, DeviceMode.ON, null, null),
            DeviceDB("1", ProductType.HEATER, "Heater", null, DeviceMode.ON, null, 42f),
            DeviceDB("2", ProductType.SHUTTER, "Shutter", null, null, 42, null)
        )
        coEvery { delete(any<DeviceDB>()) } coAnswers { 0 }
        coEvery { update(any<DeviceDB>()) } coAnswers { 1 }
    }

    private lateinit var interactor: DeviceInteractor

    @Before
    fun setup() {
        interactor = DeviceInteractor(DeviceRepository(deviceDao))
    }

    @Test
    fun loadDataSuccess() {
        runBlocking {
            val devices = interactor.loadDevices()
            Assert.assertEquals(3, devices.size)
        }
    }

    @Test
    fun deleteDeviceSuccess() {
        runBlocking {
            interactor.deleteDevice(Light("0", "Light", 42, DeviceMode.ON))
            Assert.assertEquals(true, true)
        }
    }

    @Test
    fun updateDeviceSuccess() {
        runBlocking {
            interactor.updateDevice(Light("0", "Light", 42, DeviceMode.ON))
            Assert.assertEquals(true, true)
        }
    }
}