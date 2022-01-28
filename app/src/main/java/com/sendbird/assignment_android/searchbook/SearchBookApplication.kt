package com.sendbird.assignment_android.searchbook

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SearchBookApplication: Application() {
    override fun onCreate(){
        super.onCreate()

        startKoin {
            androidContext(this@SearchBookApplication)
//            modules(myModule)
        }
    }
}