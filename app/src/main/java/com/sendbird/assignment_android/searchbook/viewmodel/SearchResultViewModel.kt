package com.sendbird.assignment_android.searchbook.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sendbird.assignment_android.searchbook.model.SearchResult
import com.sendbird.assignment_android.searchbook.repository.BookRepository
import com.sendbird.assignment_android.searchbook.util.Constants.BOOK_API_RESULT_OK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchResultViewModel(private val bookRepository: BookRepository) : BaseViewModel() {

    val searchResult: MutableLiveData<SearchResult> = MutableLiveData()

    var currentPage: MutableLiveData<Int> = MutableLiveData<Int>(1)
    var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun searchBook(query: String, page: Int = 1) {
        searchJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            isLoading.postValue(true)
            val response = bookRepository.searchBook(query, page)
            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    val result = response.body()
                    if(result?.error.equals(BOOK_API_RESULT_OK))
                        searchResult.value = result
                    else
                        onError("Response Error: ${result?.error}")
                }
                else {
                    onError("Response Error: ${response.message()}")
                }

                isLoading.value = false
            }
        }
    }

    fun onSearchButtonClick(query: String) {
        currentPage.value = 1
        searchBook(query)
    }
}