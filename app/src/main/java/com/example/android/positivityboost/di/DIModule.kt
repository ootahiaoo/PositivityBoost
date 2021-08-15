package com.example.android.positivityboost.di

import android.app.Application
import com.example.android.positivityboost.network.ApiClient
import com.example.android.positivityboost.network.QuoteApi
import com.example.android.positivityboost.repository.QuoteRepository
import com.example.android.positivityboost.ui.MainViewModel
import com.example.android.positivityboost.utils.QUOTE_RETROFIT_NAME
import com.example.android.positivityboost.utils.QUOTE_URL
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

object DIModule {

    fun startKoin(app: Application, koinApplication: KoinApplication) {
        koinApplication.run {
            androidContext(app)

            modules(
                listOf(
                    apiClientModule,
                    apiModule,
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }

    private val apiClientModule: Module = module {
        single { ApiClient() }
        single(named(QUOTE_RETROFIT_NAME)) {
            (get() as ApiClient).createRetrofitBuilder().baseUrl(QUOTE_URL).build()
        }
    }

    private val apiModule: Module = module {
        single { (get(named(QUOTE_RETROFIT_NAME)) as Retrofit).create(QuoteApi::class.java) }
    }

    private val repositoryModule: Module = module {
        single { QuoteRepository(get()) }
    }

    private val viewModelModule: Module = module {
        viewModel { MainViewModel(get()) }
    }
}