package com.noveogroup.modulotechinterview.data.di

import com.noveogroup.modulotechinterview.data.repository.DeviceRepository
import com.noveogroup.modulotechinterview.data.repository.SyncRepository
import com.noveogroup.modulotechinterview.data.repository.UserRepository
import com.noveogroup.modulotechinterview.domain.api.DeviceRepositoryApi
import com.noveogroup.modulotechinterview.domain.api.SyncRepositoryApi
import com.noveogroup.modulotechinterview.domain.api.UserRepositoryApi
import org.koin.dsl.module

val repositoryModule = module {
    single<DeviceRepositoryApi> {
        DeviceRepository(get())
    }
    single<SyncRepositoryApi> {
        SyncRepository(
            api = get(),
            deviceDao = get(),
            userDao = get(),
            preferences = get()
        )
    }
    single<UserRepositoryApi> {
        UserRepository(get())
    }
}
