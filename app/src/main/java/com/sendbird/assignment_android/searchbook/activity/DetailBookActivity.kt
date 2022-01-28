package com.sendbird.assignment_android.searchbook.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sendbird.assignment_android.searchbook.R
import com.sendbird.assignment_android.searchbook.databinding.ActivityDetailBookBinding
import com.sendbird.assignment_android.searchbook.viewmodel.DetailBookViewModel

class DetailBookActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityDetailBookBinding
    private val detailBookViewModel by viewModels<DetailBookViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_book)

        detailBookViewModel.getBook("9781800204201").subscribe(
            { result ->
                Log.d("BookAPI", "book = ${result.toString()}")
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
    }
}