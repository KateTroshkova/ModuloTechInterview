package com.noveogroup.modulotechinterview.data.di

import com.noveogroup.modulotechinterview.data.preferences.DataPreferences
import org.koin.dsl.module

val preferencesModule = module {
    single { DataPreferences(get()) }
}