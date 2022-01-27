package com.sendbird.assignment_android.searchbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendbird.assignment_android.searchbook.R
import com.sendbird.assignment_android.searchbook.databinding.ItemSearchLoadingBinding
import com.sendbird.assignment_android.searchbook.databinding.ItemSearchResultBinding
import com.sendbird.assignment_android.searchbook.model.Book

class SearchResultAdapter: RecyclerView.Adapter<SearchResultAdapter.SearchResultItemViewHolder>() {
    private val VIEW_TYPE_BOOK = 0
    private val VIEW_TYPE_LOADING = 1

    inner class SearchResultItemViewHolder(viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder(
        viewDataBinding.root
    ) {
        val binding: ViewDataBinding = viewDataBinding
    }

    private var bookList = mutableListOf<Book?>()

    fun setBooks(books: List<Book?>) {
        bookList.apply {
            clear()
            addAll(books)
        }
        notifyDataSetChanged()
    }

    fun addBook(book: Book?) {
        bookList.add(book)
        notifyItemInserted(bookList.size - 1)
    }

    fun addBooks(books: List<Book?>) {
        bookList.addAll(books)
        bookList.add(null)
        notifyDataSetChanged()
    }

    fun removeLoading() {
        this.bookList.removeAt(bookList.size - 1)
        notifyItemRemoved(bookList.size)
    }

    override fun getItemViewType(position: Int): Int {
        return when(bookList[position]) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_BOOK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultItemViewHolder {
        return when (viewType) {
            VIEW_TYPE_BOOK -> {
                val binding: ItemSearchResultBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_search_result, parent, false)
                SearchResultItemViewHolder(binding)
            }

            else -> {
                val binding: ItemSearchLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_search_loading, parent, false)
                SearchResultItemViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: SearchResultItemViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_BOOK -> {
                val book: Book? = bookList[position]
                (holder.binding as ItemSearchResultBinding).book = book

                Glide.with(holder.itemView).load(book?.image).into(holder.binding.bookCover)

                holder.binding.root.setOnClickListener { v ->
                    if (book != null) {
                    }
                }
            }

            else -> {
            }
        }


    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}