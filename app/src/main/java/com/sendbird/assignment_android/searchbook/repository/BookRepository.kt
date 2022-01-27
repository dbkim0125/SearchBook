package com.sendbird.assignment_android.searchbook.repository

import com.sendbird.assignment_android.searchbook.model.SearchResultModel
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

    fun searchBook(query: String, page: Int = 1): Single<SearchResultModel> =
        BookAPI.bookAPI
            .searchBook(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}