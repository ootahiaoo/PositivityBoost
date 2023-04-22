package fr.tahia910.android.positivityboost

import android.app.Application
import org.koin.core.context.startKoin

class PositivityBoostApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            fr.tahia910.android.positivityboost.di.DIModule.startKoin(this@PositivityBoostApplication, this)
        }
    }
}
