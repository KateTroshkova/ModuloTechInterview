package com.noveogroup.modulotechinterview.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel

class AppBarViewModel(
    savedState: SavedStateHandle
) : MvvmViewModel(savedState) {

    private val _showMenu: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val showMenu: LiveData<Boolean> by lazy { _showMenu }

    fun repeatValue() {
        _showMenu.value = _showMenu.value
    }

    fun showMenu() {
        _showMenu.value = true
    }

    fun hideMenu() {
        _showMenu.value = false
    }
}
