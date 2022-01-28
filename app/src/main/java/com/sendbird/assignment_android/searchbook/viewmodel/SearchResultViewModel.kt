package com.sendbird.assignment_android.searchbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sendbird.assignment_android.searchbook.model.SearchResult
import com.sendbird.assignment_android.searchbook.repository.BookRepository

class SearchResultViewModel(private val _application: Application) : AndroidViewModel(_application) {
    var searchResultLiveData: MutableLiveData<SearchResult?> = MutableLiveData()

    fun getSearchResult() = searchResultLiveData.value
    fun setSearchResult(searchResult: SearchResult) {
        searchResultLiveData.postValue(searchResult)
    }

    fun searchBook(query: String, page: Int = 1) = BookRepository.getInstance().searchBook(query, page)
}