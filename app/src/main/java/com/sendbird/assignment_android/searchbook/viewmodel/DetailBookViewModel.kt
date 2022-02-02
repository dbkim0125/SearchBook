package com.sendbird.assignment_android.searchbook.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.sendbird.assignment_android.searchbook.model.Book
import com.sendbird.assignment_android.searchbook.repository.BookRepository
import com.sendbird.assignment_android.searchbook.util.Constants
import com.sendbird.assignment_android.searchbook.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailBookViewModel(private val bookRepository: BookRepository) : BaseViewModel() {
    val book: MutableLiveData<Book> = MutableLiveData<Book>()

    fun getBook(isbn13: String) {
        searchJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = bookRepository.getBook(isbn13)
            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    val result = response.body()
                    if(result?.error.equals(Constants.BOOK_API_RESULT_OK))
                        book.value = result
                    else
                        onError("Response Error: ${result?.error}")
                }
                else {
                    onError("Response Error: ${response.message()}")
                }
            }
        }
    }

    fun onBuyClick(view: View, url: String) {
        Utils.openWebBrowser(view.context, url, true)
    }
}