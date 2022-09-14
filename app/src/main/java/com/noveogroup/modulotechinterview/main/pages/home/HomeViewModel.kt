package com.noveogroup.modulotechinterview.main.pages.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.common.SingleLiveData
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.entity.device.Device
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import com.noveogroup.modulotechinterview.domain.interactor.SyncInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val syncInteractor: SyncInteractor,
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    private var currentState: HomeState = HomeState(
        devices = savedState[KEY_DEVICES] ?: listOf(),
        isLightSelected = savedState[KEY_LIGHT] ?: true,
        isHeaterSelected = savedState[KEY_HEATER] ?: true,
        isShutterSelected = savedState[KEY_SHUTTER] ?: true
    )

    private val _errorEvent: SingleLiveData<Throwable> by lazy { SingleLiveData() }
    private val _state: MutableLiveData<HomeState> by lazy { MutableLiveData() }
    private val _loadingState: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val errorEvent: LiveData<Throwable> by lazy { _errorEvent }
    val state: LiveData<HomeState> by lazy { _state }
    val loadingState: LiveData<Boolean> by lazy { _loadingState }

    init {
        updateState()
    }

    override fun attach() {
        super.attach()
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loadingState.value = true
                syncInteractor.syncData()
                val devices = deviceInteractor.loadDevices()
                currentState = currentState.copy(devices = devices)
                updateState()
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
                val devices = currentState.devices.filter { it != device }
                currentState = currentState.copy(devices = devices)
                updateState()
                _loadingState.value = false
            } catch (e: Throwable) {
                _errorEvent.value = e
            }
        }
    }

    fun enableLightFilter() {
        currentState = currentState.copy(isLightSelected = !currentState.isLightSelected)
        updateState()
    }

    fun enableHeaterFilter() {
        currentState = currentState.copy(isHeaterSelected = !currentState.isHeaterSelected)
        updateState()
    }

    fun enableShutterFilter() {
        currentState = currentState.copy(isShutterSelected = !currentState.isShutterSelected)
        updateState()
    }

    private fun updateState() {
        _state.value = currentState
        savedState[KEY_DEVICES] = currentState.devices
        savedState[KEY_LIGHT] = currentState.isLightSelected
        savedState[KEY_HEATER] = currentState.isHeaterSelected
        savedState[KEY_SHUTTER] = currentState.isShutterSelected
    }

    private companion object {
        private const val KEY_DEVICES = "devices"
        private const val KEY_LIGHT = "light"
        private const val KEY_HEATER = "heater"
        private const val KEY_SHUTTER = "shutter"
    }
}
