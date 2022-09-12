package com.noveogroup.modulotechinterview.main.pages.home

import androidx.navigation.NavController
import com.noveogroup.modulotechinterview.common.navigation.Navigator
import com.noveogroup.modulotechinterview.domain.entity.device.Device
import com.noveogroup.modulotechinterview.domain.entity.device.Heater
import com.noveogroup.modulotechinterview.domain.entity.device.Light
import com.noveogroup.modulotechinterview.domain.entity.device.Shutter

class HomeNavigator(navigationController: NavController) : Navigator(navigationController) {

    fun openDevice(device: Device) {
        when (device) {
            is Light -> navigationController.navigate(
                HomeFragmentDirections.actionHomeToLight(
                    device
                )
            )
            is Heater -> navigationController.navigate(
                HomeFragmentDirections.actionHomeToHeater(
                    device
                )
            )
            is Shutter -> navigationController.navigate(
                HomeFragmentDirections.actionHomeToShutters(
                    device
                )
            )
            else -> throw IllegalArgumentException()
        }
    }
}