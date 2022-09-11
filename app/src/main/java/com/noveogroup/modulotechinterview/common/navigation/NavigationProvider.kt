package com.noveogroup.modulotechinterview.common.navigation

import android.content.ContextWrapper

interface NavigationProvider {

    val context: ContextWrapper
    val containerId: Int
    val navigator: Navigator
}
