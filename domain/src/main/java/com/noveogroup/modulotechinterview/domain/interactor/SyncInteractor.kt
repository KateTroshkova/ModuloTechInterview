package com.noveogroup.modulotechinterview.domain.interactor

import com.noveogroup.modulotechinterview.domain.api.SyncRepositoryApi

class SyncInteractor(
    private val api: SyncRepositoryApi
) {

    suspend fun syncData() = api.syncData()
}