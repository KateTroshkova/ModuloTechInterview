package com.noveogroup.modulotechinterview

import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import com.noveogroup.modulotechinterview.di.activityModule
import com.noveogroup.modulotechinterview.di.deviceFragmentModule
import com.noveogroup.modulotechinterview.di.homeFragmentModule
import com.noveogroup.modulotechinterview.di.profileFragmentModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : MultiDexApplication() {

    private val appModule = module {
        single { this }
        single<SharedPreferences> { getSharedPreferences(packageName, Context.MODE_PRIVATE) }
    }

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                activityModule,
                homeFragmentModule,
                deviceFragmentModule,
                profileFragmentModule
            )
        }
    }
}