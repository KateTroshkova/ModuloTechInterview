package com.noveogroup.modulotechinterview.common.android.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

fun <Binding : ViewBinding> ViewGroup.inflateChild(
    bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> Binding,
    attachToRoot: Boolean = false
): Binding =
    bindingFactory(context.inflater, this, attachToRoot)
