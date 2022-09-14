package com.noveogroup.modulotechinterview.main.pages.device_shutter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.common.SingleLiveData
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.entity.device.Shutter
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShutterViewModel(
    savedState: SavedStateHandle,
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel(savedState) {

    private var currentState: ShutterState = ShutterState(
        defaultShutter = savedState[KEY_DEVICE],
        position = savedState[KEY_POSITION] ?: 0
    )

    private val _state: MutableLiveData<ShutterState> by lazy { MutableLiveData() }
    private val _errorEvent: SingleLiveData<Throwable> by lazy { SingleLiveData() }
    private val _loadingState: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val state: LiveData<ShutterState> by lazy { _state }
    val errorEvent: LiveData<Throwable> by lazy { _errorEvent }
    val loadingState: LiveData<Boolean> by lazy { _loadingState }

    init {
        updateState()
    }

    fun applyState(device: Shutter) {
        if (currentState.defaultShutter == null) {
            currentState = currentState.copy(defaultShutter = device, position = device.position)
            updateState()
        }
    }

    fun updatePosition(position: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _loadingState.value = true
                currentState = currentState.copy(position = position)
                updateState()
                val shutter = currentState.defaultShutter ?: return@launch
                deviceInteractor.updateDevice(
                    Shutter(
                        shutter.id,
                        shutter.deviceName,
                        currentState.position,
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
        savedState[KEY_DEVICE] = currentState.defaultShutter
        savedState[KEY_POSITION] = currentState.position
    }

    companion object {
        private const val KEY_DEVICE = "shutter"
        private const val KEY_POSITION = "shutter_position"
    }
}
