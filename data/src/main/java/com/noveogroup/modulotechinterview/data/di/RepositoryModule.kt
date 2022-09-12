package com.noveogroup.modulotechinterview.data.di

import com.noveogroup.modulotechinterview.data.repository.DeviceRepository
import com.noveogroup.modulotechinterview.domain.api.DeviceRepositoryApi
import org.koin.dsl.module

val repositoryModule = module {
    single<DeviceRepositoryApi> {
        DeviceRepository(get())
    }
}