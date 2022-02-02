package com.sendbird.assignment_android.searchbook.di

import com.sendbird.assignment_android.searchbook.network.BookAPI
import com.sendbird.assignment_android.searchbook.repository.BookRepository
import com.sendbird.assignment_android.searchbook.viewmodel.DetailBookViewModel
import com.sendbird.assignment_android.searchbook.viewmodel.SearchResultViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://api.itbook.store/1.0/"

val appModule = module {
    //network
    single {
        Interceptor {
            val request = it.request()
                .newBuilder()
                .build()
            return@Interceptor it.proceed(request)
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookAPI::class.java)
    }

    //Repository
    single { BookRepository(get()) }

    //viewmodel
    viewModel { SearchResultViewModel(get()) }
    viewModel { DetailBookViewModel(get()) }
}