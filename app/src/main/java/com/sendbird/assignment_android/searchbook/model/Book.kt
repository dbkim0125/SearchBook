package com.sendbird.assignment_android.searchbook.model

import androidx.databinding.BaseObservable
import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("error")
    var error: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("subtitle")
    var subtitle: String,

    @SerializedName("authors")
    var authors: String,

    @SerializedName("publisher")
    var publisher: String,

    @SerializedName("language")
    var language: String,

    @SerializedName("isbn10")
    var isbn10: String,

    @SerializedName("isbn13")
    var isbn13: String,

    @SerializedName("pages")
    var pages: Int,

    @SerializedName("year")
    var year: Int,

    @SerializedName("rating")
    var rating: Int,

    @SerializedName("desc")
    var desc: String,

    @SerializedName("price")
    var price: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("url")
    var status: String
): BaseObservable()