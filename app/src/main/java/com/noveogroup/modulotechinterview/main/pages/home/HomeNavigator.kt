package com.noveogroup.modulotechinterview.main.pages.home

import androidx.navigation.NavController
import com.noveogroup.modulotechinterview.common.navigation.Navigator

class HomeNavigator(navigationController: NavController) : Navigator(navigationController) {

    fun openDevice() = navigationController.navigate(
        HomeFragmentDirections.actionHomeToDevice()
    )
}