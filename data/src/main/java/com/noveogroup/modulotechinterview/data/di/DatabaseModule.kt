package com.noveogroup.modulotechinterview.data.di

import com.noveogroup.modulotechinterview.data.database.DatabaseProvider
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseProvider(context = get()) }
    single { get<DatabaseProvider>().deviceDao }
    single { get<DatabaseProvider>().userDao }
}