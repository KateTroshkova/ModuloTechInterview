package com.noveogroup.modulotechinterview.data.di

import com.noveogroup.modulotechinterview.data.network.api.Storage42Api
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { Moshi.Builder().build() }
    single {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get()))
    }

    // OkHttpClient
    factory {
        OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
    }
    single {
        provideInventoryApi(get(), get())
    }
}

private fun provideInventoryApi(
    retrofitBuilder: Retrofit.Builder,
    okHttpClient: OkHttpClient
): Storage42Api = retrofitBuilder
    .baseUrl("https:")
    .client(okHttpClient)
    .build()
    .create(Storage42Api::class.java)

private const val timeout = 45L