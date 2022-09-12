package com.noveogroup.modulotechinterview.main.pages.device_heater

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.entity.device.Heater
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeaterViewModel(
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    private val _state: MutableLiveData<HeaterState> by lazy { MutableLiveData() }
    val state: LiveData<HeaterState> by lazy { _state }

    fun applyState(device: Heater) {
        _state.value = _state.value?.copy(defaultHeater = device)
    }

    fun updateMode(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value =
                _state.value?.copy(mode = if (isEnabled) DeviceMode.ON else DeviceMode.OFF)
            val heater = _state.value ?: return@launch
            deviceInteractor.updateDevice(
                Heater(
                    heater.defaultHeater.id,
                    heater.defaultHeater.deviceName,
                    heater.mode,
                    heater.defaultHeater.temperature
                )
            )
        }
    }

    fun updateTemperature(temperature: Float) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value =
                _state.value?.copy(temperature = temperature)
            val heater = _state.value ?: return@launch
            deviceInteractor.updateDevice(
                Heater(
                    heater.defaultHeater.id,
                    heater.defaultHeater.deviceName,
                    heater.defaultHeater.mode,
                    heater.temperature
                )
            )
        }
    }
}