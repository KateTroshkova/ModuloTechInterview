package com.noveogroup.modulotechinterview.main.pages.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.common.SingleLiveData
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.entity.device.Device
import com.noveogroup.modulotechinterview.domain.entity.type.ProductType
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import com.noveogroup.modulotechinterview.domain.interactor.SyncInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val syncInteractor: SyncInteractor,
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    private val _errorEvent: SingleLiveData<Throwable> by lazy { SingleLiveData() }
    private val _state: MutableLiveData<HomeState> by lazy {
        MutableLiveData(
            HomeState(
                devices = savedState[KEY_DEVICES]
                    ?: listOf<Device>().also { savedState[KEY_DEVICES] = it },
                filteredDevices = listOf(),
                isLightSelected = savedState[KEY_LIGHT] ?: true.also { savedState[KEY_LIGHT] = it },
                isHeaterSelected = savedState[KEY_HEATER] ?: true.also {
                    savedState[KEY_HEATER] = it
                },
                isShutterSelected = savedState[KEY_SHUTTER] ?: true.also {
                    savedState[KEY_SHUTTER] = it
                }
            )
        )
    }
    private val _loadingState: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val errorEvent: LiveData<Throwable> by lazy { _errorEvent }
    val state: LiveData<HomeState> by lazy { _state }
    val loadingState: LiveData<Boolean> by lazy { _loadingState }

    override fun attach() {
        super.attach()
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loadingState.value = true
                syncInteractor.syncData()
                val devices = deviceInteractor.loadDevices()
                _state.value = _state.value?.copy(
                    devices = devices,
                    filteredDevices = devices.filter {
                        when (it.productType) {
                            ProductType.LIGHT -> _state.value?.isLightSelected ?: false
                            ProductType.SHUTTER -> _state.value?.isShutterSelected ?: false
                            ProductType.HEATER -> _state.value?.isHeaterSelected ?: false
                        }
                    }
                )
                savedState[KEY_DEVICES] = devices
                _loadingState.value = false
            } catch (e: Throwable) {
                _errorEvent.value = e
            }
        }
    }

    fun deleteDevice(device: Device) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loadingState.value = true
                deviceInteractor.deleteDevice(device)
                val devices = _state.value?.devices?.filter { it != device } ?: listOf()
                _state.value = _state.value?.copy(
                    devices = devices,
                    filteredDevices = devices.filter {
                        when (it.productType) {
                            ProductType.LIGHT -> _state.value?.isLightSelected ?: false
                            ProductType.SHUTTER -> _state.value?.isShutterSelected ?: false
                            ProductType.HEATER -> _state.value?.isHeaterSelected ?: false
                        }
                    }
                )
                savedState[KEY_DEVICES] = devices
                _loadingState.value = false
            } catch (e: Throwable) {
                _errorEvent.value = e
            }
        }
    }

    fun enableLightFilter() {
        val oldValue = _state.value ?: return
        val newValue = oldValue.copy(isLightSelected = !oldValue.isLightSelected)
        _state.value = newValue
        savedState[KEY_LIGHT] = newValue.isLightSelected
    }

    fun enableHeaterFilter() {
        val oldValue = _state.value ?: return
        val newValue = oldValue.copy(isHeaterSelected = !oldValue.isHeaterSelected)
        _state.value = newValue
        savedState[KEY_HEATER] = newValue.isHeaterSelected
    }

    fun enableShutterFilter() {
        val oldValue = _state.value ?: return
        val newValue = oldValue.copy(isShutterSelected = !oldValue.isShutterSelected)
        _state.value = newValue
        savedState[KEY_SHUTTER] = newValue.isShutterSelected
    }

    private companion object {
        private const val KEY_DEVICES = "devices"
        private const val KEY_LIGHT = "light"
        private const val KEY_HEATER = "heater"
        private const val KEY_SHUTTER = "shutter"
    }
}
