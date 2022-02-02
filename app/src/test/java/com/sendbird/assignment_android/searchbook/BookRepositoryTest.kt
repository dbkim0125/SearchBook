package com.sendbird.assignment_android.searchbook

import com.sendbird.assignment_android.searchbook.repository.BookRepository
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.component.inject

class BookRepositoryTest: AbstractKoinTest() {
    private val bookRepository: BookRepository by inject()

    @Test
    fun testSearchBook() = runBlocking {
        val searchResult = bookRepository.searchBook("iot").body()
        assertTrue(searchResult != null)
        assertTrue(searchResult?.error == "0")
    }

    @Test
    fun testGetBook() = runBlocking {
        val book = bookRepository.getBook("9781839214806").body()
        assertTrue(book != null)
        assertTrue(book!!.isbn13 == "9781839214806")
    }
}