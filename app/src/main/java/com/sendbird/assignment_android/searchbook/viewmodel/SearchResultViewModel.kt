package com.sendbird.assignment_android.searchbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sendbird.assignment_android.searchbook.model.Book
import com.sendbird.assignment_android.searchbook.model.SearchResultModel
import com.sendbird.assignment_android.searchbook.repository.BookRepository

class SearchResultViewModel(private val _application: Application) : AndroidViewModel(_application) {
    var searchResultModelLiveData: MutableLiveData<SearchResultModel?> = MutableLiveData()

    fun getSearchResultModel() = searchResultModelLiveData.value
    fun setSearchResultModel(searchResultModel: SearchResultModel) {
        searchResultModelLiveData.postValue(searchResultModel)
    }

    fun searchBook(query: String, page: Int = 1) = BookRepository.getInstance().searchBook(query, page)
}