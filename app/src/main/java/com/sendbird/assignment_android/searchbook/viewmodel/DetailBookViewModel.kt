package com.sendbird.assignment_android.searchbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sendbird.assignment_android.searchbook.repository.BookRepository

class DetailBookViewModel(private val _application: Application) : AndroidViewModel(_application) {

    fun getBook(isbn13: String) = BookRepository.getInstance().getBook(isbn13)
}