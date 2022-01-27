package com.sendbird.assignment_android.searchbook.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sendbird.assignment_android.searchbook.R
import com.sendbird.assignment_android.searchbook.adapter.SearchResultAdapter
import com.sendbird.assignment_android.searchbook.databinding.FragmentSearchResultListBinding
import com.sendbird.assignment_android.searchbook.model.Book
import com.sendbird.assignment_android.searchbook.viewmodel.SearchResultViewModel
import io.reactivex.disposables.CompositeDisposable

class SearchResultListFragment: Fragment() {

    private lateinit var binding: FragmentSearchResultListBinding
    private val searchResultViewModel by activityViewModels<SearchResultViewModel>()

    private lateinit var searchResultAdapter: SearchResultAdapter

    private var currentPage: Int = 1
    private var isLoading: Boolean = false

    private var compositeDisposable = CompositeDisposable()

    companion object {
        fun show(fm: FragmentManager) {
            val searchResultListFragment = SearchResultListFragment()

            fm.popBackStack(
                SearchResultListFragment::class.java.simpleName,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            val ft = fm.beginTransaction()
            ft.add(
                R.id.container,
                searchResultListFragment,
                SearchResultListFragment::class.java.simpleName
            )
            ft.addToBackStack(SearchResultListFragment::class.java.simpleName)
            ft.commitAllowingStateLoss()
        }
    }

    //현재 선택된 Book을 위한 Observer
    private val bookObserver = Observer { book: Book ->
        SearchResultDetailFragment.show(requireActivity().supportFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_result_list,
            container,
            false
        )

        searchResultAdapter = SearchResultAdapter()
        binding.searchResultList.adapter = searchResultAdapter

        //Check if list is scroll end
        binding.searchResultList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                if(!binding.searchResultList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    searchResultAdapter.removeLoading()
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

        return binding.root
    }

    //처음 검색
    fun searchBook(keyword: String) {
        isLoading = true
        compositeDisposable.add(
                searchResultViewModel.searchBook(keyword, currentPage)
                        .subscribe(
                                { result ->
                                    Log.d("BookAPI", result.toString())
                                    searchResultViewModel.setSearchResultModel(result)

                                    if(result.books.isNotEmpty()) {
                                        searchResultAdapter.addBooks(result.books)
                                        currentPage++
                                    }
                                    else {
                                        searchResultAdapter.removeLoading()
                                    }

                                    isLoading = false
                                },
                                { error ->
                                    isLoading = false
                                    Log.e("BookAPI", error.message ?: "")
                                    Toast.makeText(
                                            context,
                                            "네트워크 오류가 있습니다. 다시 시도해주세요.",
                                            Toast.LENGTH_LONG
                                    ).show()
                                }
                        ))
    }

    //다음 페이지 요
    fun loadMoreDocuments() {
        if (++currentPage <= 50) {
            isLoading = true
            searchResultAdapter.addBook(null)
            compositeDisposable.add(
                    searchResultViewModel.searchBook(binding.searchEdit.text.toString(), currentPage)
                    .subscribe(
                        { result ->
                            Log.d("SearchAPI", result.toString())
                            searchResultViewModel.setSearchResultModel(result)
                            searchResultAdapter.removeLoading()
                            searchResultAdapter.addBooks(result.books)
                            isLoading = false
                        },
                        { error ->
                            isLoading = false
                            Log.e("SearchAPI", error.message ?: "")
                            Toast.makeText(
                                context,
                                "네트워크 오류가 있습니다. 다시 시도해주세요.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ))
        }
    }

    //toggle show of soft keyboard
    private fun toggleSoftKeyboard(show: Boolean) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show && activity?.currentFocus != null) {
            imm.showSoftInput(activity?.currentFocus, InputMethodManager.SHOW_IMPLICIT)
        } else {
            imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        }
    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.dispose()
    }
}