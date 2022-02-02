package com.sendbird.assignment_android.searchbook.model

import com.google.gson.annotations.SerializedName

data class SearchResult (
    @SerializedName("error")
    var error: String,

    @SerializedName("total")
    var total: Int,

    @SerializedName("page")
    var page: Int,

    @SerializedName("books")
    var books: List<Book>
)
