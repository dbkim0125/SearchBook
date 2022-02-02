package com.sendbird.assignment_android.searchbook.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object Utils {
    fun openWebBrowser(context: Context, url: String, isNewTask: Boolean) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (isNewTask) intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}