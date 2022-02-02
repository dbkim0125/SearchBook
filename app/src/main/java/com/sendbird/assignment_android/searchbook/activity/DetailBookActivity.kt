package com.sendbird.assignment_android.searchbook.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.sendbird.assignment_android.searchbook.R
import com.sendbird.assignment_android.searchbook.databinding.ActivityDetailBookBinding
import com.sendbird.assignment_android.searchbook.model.Book
import com.sendbird.assignment_android.searchbook.util.Constants
import com.sendbird.assignment_android.searchbook.viewmodel.DetailBookViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailBookActivity: BaseActivity()  {
    private lateinit var binding: ActivityDetailBookBinding
    private val detailBookViewModel by viewModel<DetailBookViewModel>()

    //Observe book data
    private val bookObserver = Observer<Book> {
        binding.book = it
        Glide.with(this@DetailBookActivity).load(it.image).into(binding.bookCover)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_book)
        binding.vm = detailBookViewModel

        binding.back.setOnClickListener { finish() }

        val isbn13 = intent.getStringExtra(Constants.EXTRA_ISBN13)
        isbn13?.let { detailBookViewModel.getBook(isbn13) }

        detailBookViewModel.book.observe(this, bookObserver)
        detailBookViewModel.errorMessage.observe(this, errorObserver)
    }
}