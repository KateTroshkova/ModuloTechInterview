package com.noveogroup.modulotechinterview.di

import com.noveogroup.modulotechinterview.main.MainActivity
import com.noveogroup.modulotechinterview.main.MainViewModel
import com.noveogroup.modulotechinterview.main.pages.device.DeviceFragment
import com.noveogroup.modulotechinterview.main.pages.device.DeviceViewModel
import com.noveogroup.modulotechinterview.main.pages.home.HomeFragment
import com.noveogroup.modulotechinterview.main.pages.home.HomeViewModel
import com.noveogroup.modulotechinterview.main.pages.profile.ProfileFragment
import com.noveogroup.modulotechinterview.main.pages.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {

    scope<MainActivity> {
        viewModel {
            MainViewModel()
        }
    }
}

val homeFragmentModule = module {
    scope<HomeFragment> {
        viewModel {
            HomeViewModel()
        }
    }
}

val deviceFragmentModule = module {
    scope<DeviceFragment> {
        viewModel {
            DeviceViewModel()
        }
    }
}

val profileFragmentModule = module {
    scope<ProfileFragment> {
        viewModel {
            ProfileViewModel()
        }
    }
}
