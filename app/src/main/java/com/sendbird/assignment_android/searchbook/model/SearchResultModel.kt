package com.sendbird.assignment_android.searchbook.model

import com.google.gson.annotations.SerializedName

data class SearchResultModel (
    @SerializedName("error")
    var error: Int,

    @SerializedName("total")
    var total: Int,

    @SerializedName("page")
    var page: Int,

    @SerializedName("books")
    var books: List<Book>
)
