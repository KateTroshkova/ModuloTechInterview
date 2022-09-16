package com.noveogroup.modulotechinterview.common.android.ext

import android.content.Context
import android.view.LayoutInflater

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)
