package com.sendbird.assignment_android.searchbook

import android.app.Application
import com.sendbird.assignment_android.searchbook.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SearchBookApplication: Application() {
    override fun onCreate(){
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SearchBookApplication)
            modules(appModule)
        }
    }
}