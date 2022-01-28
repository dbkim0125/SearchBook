package com.sendbird.assignment_android.searchbook.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sendbird.assignment_android.searchbook.R
import com.sendbird.assignment_android.searchbook.adapter.SearchResultAdapter
import com.sendbird.assignment_android.searchbook.databinding.ActivitySearchBinding
import com.sendbird.assignment_android.searchbook.viewmodel.SearchResultViewModel
import io.reactivex.disposables.CompositeDisposable

class SearchActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val searchResultViewModel by viewModels<SearchResultViewModel>()

    private lateinit var searchResultAdapter: SearchResultAdapter

    private var currentPage: Int = 1
    private var isLoading: Boolean = false

    private var compositeDisposable = CompositeDisposable()

    private var backPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        searchResultAdapter = SearchResultAdapter()
        binding.searchResultList.adapter = searchResultAdapter

        //Check if list is scroll end
        binding.searchResultList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                if(!isLoading && !binding.searchResultList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    binding.searchResultList.post { searchResultAdapter.removeLoading() }
                    searchBook(binding.searchEdit.text.toString())
                }
            }
        })

        binding.searchEdit.setOnEditorActionListener { textView: TextView, actionId: Int, keyEvent: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (textView.text.isNotEmpty()) {
                    toggleSoftKeyboard(false)
                    currentPage = 1
                    searchBook(textView.text.toString())
                }
                true
            }
            false
        }
    }

    private fun searchBook(keyword: String) {
        isLoading = true
        compositeDisposable.add(
            searchResultViewModel.searchBook(keyword, currentPage)
                .doFinally { isLoading = false }
                .subscribe(
                    { result ->
                        Log.d("BookAPI", "total = ${result.total}, page = ${result.page}, books = ${result.books.size}, currentPage = $currentPage")
                        searchResultViewModel.setSearchResult(result)

                        if(result.books.isNotEmpty()) {
                            searchResultAdapter.addBooks(result.books)
                            currentPage++
                        }
                        else {
                            searchResultAdapter.removeLoading()
                        }
                    },
                    { error ->
                        Log.e("BookAPI", error.message ?: "")
                        Toast.makeText(
                            this,
                            "네트워크 오류가 있습니다. 다시 시도해주세요.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
        )
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

    override fun onStop() {
        super.onStop()

        compositeDisposable.dispose()
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