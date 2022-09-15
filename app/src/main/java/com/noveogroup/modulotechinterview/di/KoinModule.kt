package com.noveogroup.modulotechinterview.di

import androidx.lifecycle.SavedStateHandle
import com.noveogroup.modulotechinterview.domain.interactor.DeviceInteractor
import com.noveogroup.modulotechinterview.domain.interactor.SyncInteractor
import com.noveogroup.modulotechinterview.domain.interactor.UserInteractor
import com.noveogroup.modulotechinterview.main.AppBarViewModel
import com.noveogroup.modulotechinterview.main.MainActivity
import com.noveogroup.modulotechinterview.main.MainViewModel
import com.noveogroup.modulotechinterview.main.pages.device_heater.HeaterFragment
import com.noveogroup.modulotechinterview.main.pages.device_heater.HeaterViewModel
import com.noveogroup.modulotechinterview.main.pages.device_light.LightFragment
import com.noveogroup.modulotechinterview.main.pages.device_light.LightViewModel
import com.noveogroup.modulotechinterview.main.pages.device_shutter.ShutterFragment
import com.noveogroup.modulotechinterview.main.pages.device_shutter.ShutterViewModel
import com.noveogroup.modulotechinterview.main.pages.home.HomeFragment
import com.noveogroup.modulotechinterview.main.pages.home.HomeViewModel
import com.noveogroup.modulotechinterview.main.pages.profile.ProfileFragment
import com.noveogroup.modulotechinterview.main.pages.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {

    scope<MainActivity> {
        viewModel {
            MainViewModel(SavedStateHandle())
        }
        viewModel { AppBarViewModel(SavedStateHandle()) }
    }
}

val homeFragmentModule = module {
    scope<HomeFragment> {
        viewModel {
            HomeViewModel(SavedStateHandle(), get(), get())
        }
    }
}

val deviceHeaterFragmentModule = module {
    scope<HeaterFragment> {
        viewModel {
            HeaterViewModel(SavedStateHandle(), get())
        }
    }
}

val deviceLightFragmentModule = module {
    scope<LightFragment> {
        viewModel {
            LightViewModel(SavedStateHandle(), get())
        }
    }
}

val deviceShuttersFragmentModule = module {
    scope<ShutterFragment> {
        viewModel {
            ShutterViewModel(SavedStateHandle(), get())
        }
    }
}

val profileFragmentModule = module {
    scope<ProfileFragment> {
        viewModel {
            ProfileViewModel(SavedStateHandle(), get())
        }
    }
}

val interceptorModule = module {
    single { DeviceInteractor(get()) }
    single { SyncInteractor(get()) }
    single { UserInteractor(get()) }
}