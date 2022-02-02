package com.sendbird.assignment_android.searchbook

import android.app.Application
import com.sendbird.assignment_android.searchbook.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SearchBookApplication: Application() {
    override fun onCreate(){
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@SearchBookApplication)
            modules(appModule)
        }
    }
}