package com.noveogroup.modulotechinterview.data.network.api

import com.noveogroup.modulotechinterview.data.network.response.ApiResponse
import retrofit2.http.GET

interface Storage42Api {

    @GET(ENDPOINT_LOAD_ALL)
    suspend fun loadAllData(): ApiResponse

    companion object {
        private const val ENDPOINT_LOAD_ALL = "data.json"
    }
}