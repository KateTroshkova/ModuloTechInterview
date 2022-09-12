package com.noveogroup.modulotechinterview.main

import androidx.navigation.NavController
import com.noveogroup.modulotechinterview.common.navigation.Navigator
import com.noveogroup.modulotechinterview.main.pages.home.HomeFragmentDirections

class MainNavigator(navigationController: NavController) : Navigator(navigationController) {
    fun openProfile() = navigationController.navigate(
        HomeFragmentDirections.actionHomeToProfile()
    )

    fun back() = navigationController.navigateUp()
}