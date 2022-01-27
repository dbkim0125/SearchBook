package com.sendbird.assignment_android.searchbook.databinding.adapter

import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("setDateFormat")
    fun setDateFormat(@NonNull textView: TextView, dateString: String) {
        val dfISO8601: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        val date: Date? = dfISO8601.parse(dateString)
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        if(date != null)
            textView.text = df.format(date)
    }
}