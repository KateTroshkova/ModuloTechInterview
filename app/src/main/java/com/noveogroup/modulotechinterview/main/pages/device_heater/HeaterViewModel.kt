package com.noveogroup.modulotechinterview.main.pages.device_heater

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.common.SingleLiveData
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.entity.device.Heater
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeaterViewModel(
    savedState: SavedStateHandle,
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel(savedState) {

    private var currentState: HeaterState =
        HeaterState(
            defaultHeater = this.savedState[KEY_DEVICE],
            mode = this.savedState[KEY_MODE] ?: DeviceMode.OFF,
            temperature = this.savedState[KEY_TEMPERATURE] ?: MIN_VALUE
        )

    private val _state: MutableLiveData<HeaterState> by lazy { MutableLiveData() }
    private val _errorEvent: SingleLiveData<Throwable> by lazy { SingleLiveData() }
    private val _loadingState: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val state: LiveData<HeaterState> by lazy { _state }
    val errorEvent: LiveData<Throwable> by lazy { _errorEvent }
    val loadingState: LiveData<Boolean> by lazy { _loadingState }

    init {
        updateState()
    }

    fun applyState(device: Heater) {
        if (currentState.defaultHeater == null) {
            currentState = currentState.copy(
                defaultHeater = device,
                mode = device.mode,
                temperature = device.temperature
            )
            updateState()
        }
    }

    fun updateMode(isEnabled: Boolean) {
        currentState = currentState.copy(mode = if (isEnabled) DeviceMode.ON else DeviceMode.OFF)
        update()
        updateState()
    }

    fun updateTemperature(temperature: Float) {
        currentState = currentState.copy(temperature = temperature)
        update()
        updateState()
    }

    private fun update() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loadingState.value = true
                val heater = currentState.defaultHeater ?: return@launch
                deviceInteractor.updateDevice(
                    Heater(
                        heater.id,
                        heater.deviceName,
                        currentState.mode,
                        currentState.temperature
                    )
                )
                _loadingState.value = false
            } catch (e: Throwable) {
                _errorEvent.value = e
            }
        }
    }

    private fun updateState() {
        _state.value = currentState
        savedState[KEY_DEVICE] = currentState.defaultHeater
        savedState[KEY_MODE] = currentState.mode
        savedState[KEY_TEMPERATURE] = currentState.temperature
    }

    companion object {
        private const val KEY_DEVICE = "heater"
        private const val KEY_MODE = "heater_mode"
        private const val KEY_TEMPERATURE = "heater_temperature"
        private const val MIN_VALUE = 7f
    }
}