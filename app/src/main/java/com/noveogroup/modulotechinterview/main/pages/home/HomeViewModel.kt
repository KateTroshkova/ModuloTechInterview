package com.noveogroup.modulotechinterview.main.pages.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.noveogroup.modulotechinterview.common.SingleLiveData
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val deviceInteractor: DeviceInteractor
) : MvvmViewModel() {

    private val _errorEvent: SingleLiveData<Throwable> by lazy { SingleLiveData() }
    val errorEvent: LiveData<Throwable> by lazy { _errorEvent }

    override fun attach() {
        super.attach()
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val devices = deviceInteractor.loadDevices()
            } catch (e: Throwable) {
                _errorEvent.value = e
            }
        }
    }
}