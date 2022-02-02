package com.sendbird.assignment_android.searchbook.activity

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

open class BaseActivity: AppCompatActivity() {
    /**
     * Handle error message.
     */
    protected val errorObserver = Observer<String> {
        Log.e("BookAPI", it ?: "")
        Toast.makeText(
            this,
            "$it Please try again later.",
            Toast.LENGTH_LONG
        ).show()
    }
}