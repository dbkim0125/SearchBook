package com.sendbird.assignment_android.searchbook.repository

import com.sendbird.assignment_android.searchbook.model.Book
import com.sendbird.assignment_android.searchbook.model.SearchResult
import com.sendbird.assignment_android.searchbook.network.BookAPI
import retrofit2.Response

class BookRepository(private val bookAPI: BookAPI) {

    suspend fun searchBook(query: String, page: Int = 1): Response<SearchResult> =
        bookAPI.searchBook(query, page)

    suspend fun getBook(isbn13: String): Response<Book> =
        bookAPI.getBook(isbn13)
}