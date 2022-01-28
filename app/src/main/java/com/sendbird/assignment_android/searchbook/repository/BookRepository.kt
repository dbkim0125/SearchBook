package com.sendbird.assignment_android.searchbook.repository

import com.sendbird.assignment_android.searchbook.model.Book
import com.sendbird.assignment_android.searchbook.model.SearchResult
import com.sendbird.assignment_android.searchbook.network.BookAPI
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookRepository private constructor() {
    companion object {
        private var INSTANCE: BookRepository? = null
        fun getInstance(): BookRepository {
            if (INSTANCE == null) {
                INSTANCE = BookRepository()
            }
            return INSTANCE!!
        }
    }

    fun searchBook(query: String, page: Int = 1): Single<SearchResult> =
        BookAPI.bookAPI
            .searchBook(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getBook(isbn13: String): Single<Book> =
        BookAPI.bookAPI
            .getBook(isbn13)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}