package com.noveogroup.modulotechinterview.common.architecture

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent

abstract class MvvmViewModel(
    protected val savedState: SavedStateHandle
) : ViewModel(), KoinComponent {

    open fun attach() {
    }

    open fun detach() {
    }
}
