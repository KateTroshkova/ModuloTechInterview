package com.noveogroup.modulotechinterview.main.pages.device_light

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.common.SingleLiveData
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.entity.device.Light
import com.noveogroup.modulotechinterview.domain.entity.type.DeviceMode
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LightViewModel(
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    private var currentState = LightState(
        defaultLight = savedState[KEY_DEVICE],
        mode = savedState[KEY_MODE] ?: DeviceMode.OFF,
        intensity = savedState[KEY_INTENSITY] ?: 0
    )

    private val _state: MutableLiveData<LightState> by lazy { MutableLiveData() }
    private val _errorEvent: SingleLiveData<Throwable> by lazy { SingleLiveData() }
    private val _loadingState: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val state: LiveData<LightState> by lazy { _state }
    val errorEvent: LiveData<Throwable> by lazy { _errorEvent }
    val loadingState: LiveData<Boolean> by lazy { _loadingState }

    init {
        updateState()
    }

    fun applyState(device: Light) {
        if (currentState.defaultLight == null) {
            currentState = currentState.copy(
                defaultLight = device,
                mode = device.mode,
                intensity = device.intensity
            )
            updateState()
        }
    }

    fun updateMode(isEnabled: Boolean) {
        currentState = currentState.copy(mode = if (isEnabled) DeviceMode.ON else DeviceMode.OFF)
        update()
        updateState()
    }

    fun updateIntensity(intensity: Int) {
        currentState = currentState.copy(intensity = intensity)
        update()
        updateState()
    }

    private fun update() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loadingState.value = true
                val light = currentState.defaultLight ?: return@launch
                deviceInteractor.updateDevice(
                    Light(
                        light.id,
                        light.deviceName,
                        currentState.intensity,
                        currentState.mode,
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
        savedState[KEY_DEVICE] = currentState.defaultLight
        savedState[KEY_MODE] = currentState.mode
        savedState[KEY_INTENSITY] = currentState.intensity
    }

    companion object {
        private const val KEY_DEVICE = "device"
        private const val KEY_MODE = "mode"
        private const val KEY_INTENSITY = "intensity"
    }
}