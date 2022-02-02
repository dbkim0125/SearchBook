package com.sendbird.assignment_android.searchbook.network

import com.sendbird.assignment_android.searchbook.model.Book
import com.sendbird.assignment_android.searchbook.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookAPI {
    @GET("search/{query}/{page}")
    suspend fun searchBook(
        @Path("query") query: String,
        @Path("page") page: Int = 1
    ): Response<SearchResult>

    @GET("books/{isbn13}")
    suspend fun getBook(
        @Path("isbn13") isbn13: String
    ): Response<Book>
}