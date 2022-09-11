package com.noveogroup.modulotechinterview.common.android.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

fun ViewGroup.inflateChild(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): View =
    context.inflater.inflate(layoutId, this, attachToRoot)

fun <Binding : ViewBinding> ViewGroup.inflateChild(
    bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> Binding,
    attachToRoot: Boolean = false
): Binding =
    bindingFactory(context.inflater, this, attachToRoot)
