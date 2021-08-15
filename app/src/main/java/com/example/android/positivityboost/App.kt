package com.example.android.positivityboost

import android.app.Application
import com.example.android.positivityboost.di.DIModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            DIModule.startKoin(this@App, this)
        }
    }
}