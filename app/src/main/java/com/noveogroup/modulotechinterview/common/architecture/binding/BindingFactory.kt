package com.noveogroup.modulotechinterview.common.architecture.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

fun interface BindingFactory<Binding : ViewBinding> {
    fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean,
    ): Binding
}
