package com.sendbird.assignment_android.searchbook.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.sendbird.assignment_android.searchbook.R
import com.sendbird.assignment_android.searchbook.databinding.FragmentSearchResultDetailBinding
import com.sendbird.assignment_android.searchbook.viewmodel.SearchResultViewModel

class SearchResultDetailFragment: Fragment() {
    private lateinit var binding: FragmentSearchResultDetailBinding
    private val searchResultViewModel by activityViewModels<SearchResultViewModel>()

    companion object {
        fun show(fm: FragmentManager) {
            val searchResultDetailFragment = SearchResultDetailFragment()

            fm.popBackStack(SearchResultDetailFragment::class.java.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val ft = fm.beginTransaction()
            ft.add(R.id.container, searchResultDetailFragment, SearchResultDetailFragment::class.java.simpleName)
            ft.addToBackStack(SearchResultDetailFragment::class.java.simpleName)
            ft.commitAllowingStateLoss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result_detail, container, false)
//        binding.book = searchResultViewModel.getBook()

//        Glide.with(this).load(searchResultViewModel.getBook()?.image).into(binding.bookCover)

        binding.back.setOnClickListener { parentFragmentManager.popBackStack() }

        return binding.root
    }
}