package com.sendbird.assignment_android.searchbook.network

import com.sendbird.assignment_android.searchbook.model.Book
import com.sendbird.assignment_android.searchbook.model.SearchResult
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookAPI {

    companion object {
        private const val BASE_URL = "https://api.itbook.store/1.0/"

        val bookAPI: BookAPI = initBookAPI()

        private fun initBookAPI(): BookAPI {
            val headerInterceptor = Interceptor {
                val request = it.request()
                        .newBuilder()
                        .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                    .addInterceptor(headerInterceptor)
                    .build()

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BookAPI::class.java)
        }
    }

    @GET("search/{query}/{page}")
    fun searchBook(
        @Path("query") query: String,
        @Path("page") page: Int = 1
    ): Single<SearchResult>

    @GET("books/{isbn13}")
    fun getBook(
        @Path("isbn13") isbn13: String
    ): Single<Book>
}