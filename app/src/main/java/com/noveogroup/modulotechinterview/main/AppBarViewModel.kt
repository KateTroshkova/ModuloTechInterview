package com.noveogroup.modulotechinterview.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noveogroup.modulotechinterview.common.architecture.MvvmViewModel

class AppBarViewModel : MvvmViewModel() {

    private val _showMenu: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val showMenu: LiveData<Boolean> by lazy { _showMenu }

    fun showMenu() {
        _showMenu.value = true
    }

    fun hideMenu() {
        _showMenu.value = false
    }
}
