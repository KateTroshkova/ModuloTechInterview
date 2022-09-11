package com.noveogroup.modulotechinterview.common.architecture

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent

abstract class MvvmViewModel : ViewModel(), KoinComponent {

    protected abstract val tag: String
    var savedState: SavedStateHandle = SavedStateHandle()

    open fun attach() {
    }

    open fun detach() {
    }
}
