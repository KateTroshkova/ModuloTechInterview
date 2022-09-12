package com.noveogroup.modulotechinterview.main.pages.device_shutter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.entity.device.Shutter
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShutterViewModel(
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    private val _state: MutableLiveData<ShutterState> by lazy { MutableLiveData() }
    val state: LiveData<ShutterState> by lazy { _state }

    fun applyState(device: Shutter) {
        _state.value = _state.value?.copy(defaultShutter = device)
    }

    fun updatePosition(position: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value =
                _state.value?.copy(position = position)
            val shutter = _state.value ?: return@launch
            try {
                deviceInteractor.updateDevice(
                    Shutter(
                        shutter.defaultShutter.id,
                        shutter.defaultShutter.deviceName,
                        shutter.position,
                    )
                )
            } catch (e: Throwable) {

            }
        }
    }
}
