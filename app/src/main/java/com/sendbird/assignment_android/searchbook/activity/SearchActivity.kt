package com.sendbird.assignment_android.searchbook.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sendbird.assignment_android.searchbook.R
import com.sendbird.assignment_android.searchbook.adapter.SearchResultAdapter
import com.sendbird.assignment_android.searchbook.databinding.ActivitySearchBinding
import com.sendbird.assignment_android.searchbook.model.Book
import com.sendbird.assignment_android.searchbook.model.SearchResult
import com.sendbird.assignment_android.searchbook.util.Constants
import com.sendbird.assignment_android.searchbook.util.Utils
import com.sendbird.assignment_android.searchbook.viewmodel.SearchResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity: BaseActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val searchResultViewModel by viewModel<SearchResultViewModel>()

    private lateinit var searchResultAdapter: SearchResultAdapter

    private var backPressed = false

    //Implementation of click listener
    private val onBookClickListener = object: SearchResultAdapter.OnBookClickListener {
        override fun onSelectBook(book: Book) {
            val intent = Intent(this@SearchActivity, DetailBookActivity::class.java)
            intent.putExtra(Constants.EXTRA_ISBN13, book.isbn13)
            startActivity(intent)
        }

        override fun onBuyBook(book: Book) {
            Utils.openWebBrowser(this@SearchActivity, book.url, true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        searchResultAdapter = SearchResultAdapter(this, onBookClickListener)
        binding.searchResultList.adapter = searchResultAdapter

        binding.searchResultList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                //If it isn't loading state and recyclerview is scroll end, search more
                if(searchResultViewModel.isLoading.value != null
                    && searchResultViewModel.isLoading.value == false
                    && !binding.searchResultList.canScrollVertically(1)
                    && lastVisibleItemPosition == itemTotalCount) {
                        binding.searchResultList.post { searchResultAdapter.removeLoadingIfExist() }
                        searchBook(binding.searchEdit.text.toString())
                }
            }
        })

        binding.searchEdit.setOnEditorActionListener { textView: TextView, actionId: Int, keyEvent: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.searchButton.performClick()
                true
            }
            false
        }

        binding.searchButton.setOnClickListener {
            if (binding.searchEdit.text.isNotEmpty()) {
                toggleSoftKeyboard(false)
                searchResultAdapter.setBooks(listOf())
                searchResultViewModel.onSearchButtonClick(binding.searchEdit.text.toString())
            }
        }

        searchResultViewModel.searchResult.observe(this, searchObserver)
        searchResultViewModel.errorMessage.observe(this, errorObserver)
    }

    //Observe search result
    private val searchObserver = Observer<SearchResult> {
        if(it.error == Constants.BOOK_API_RESULT_OK && it.books.isNotEmpty()
            && it.page == searchResultViewModel.currentPage.value) {
            searchResultAdapter.addBooks(it.books)
            searchResultViewModel.currentPage.value = searchResultViewModel.currentPage.value!! + 1
        }
        else {
            searchResultAdapter.removeLoadingIfExist()
        }
    }

    private fun searchBook(keyword: String) {
        searchResultViewModel.searchBook(keyword, searchResultViewModel.currentPage.value ?: 1)
    }

    //toggle show of soft keyboard
    private fun toggleSoftKeyboard(show: Boolean) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show && currentFocus != null) {
            imm.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT)
        } else {
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun onBackPressed() {
        if (backPressed) {
            super.onBackPressed()
            return
        }
        backPressed = true
        Toast.makeText(this, R.string.warning_exit, Toast.LENGTH_LONG).show()
        Handler(Looper.getMainLooper()).postDelayed({ backPressed = false }, 2000L)
    }

}