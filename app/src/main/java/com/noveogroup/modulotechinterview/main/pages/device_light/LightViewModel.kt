package com.noveogroup.modulotechinterview.main.pages.device_light

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.entity.device.Light
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LightViewModel(
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    private val _state: MutableLiveData<LightState> by lazy { MutableLiveData() }
    val state: LiveData<LightState> by lazy { _state }

    fun applyState(device: Light) {
        _state.value = _state.value?.copy(defaultLight = device)
    }

    fun updateMode(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value =
                _state.value?.copy(mode = if (isEnabled) DeviceMode.ON else DeviceMode.OFF)
            val light = _state.value ?: return@launch
            deviceInteractor.updateDevice(
                Light(
                    light.defaultLight.id,
                    light.defaultLight.deviceName,
                    light.defaultLight.intensity,
                    light.mode,
                )
            )
        }
    }

    fun updateIntensity(intensity: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value =
                _state.value?.copy(intensity = intensity)
            val light = _state.value ?: return@launch
            deviceInteractor.updateDevice(
                Light(
                    light.defaultLight.id,
                    light.defaultLight.deviceName,
                    light.intensity,
                    light.defaultLight.mode,
                )
            )
        }
    }
}