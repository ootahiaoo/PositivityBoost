package fr.tahia910.android.positivityboost.di

import android.app.Application
import fr.tahia910.android.positivityboost.network.ApiClient
import fr.tahia910.android.positivityboost.network.DogApi
import fr.tahia910.android.positivityboost.network.QuoteApi
import fr.tahia910.android.positivityboost.repository.DogRepository
import fr.tahia910.android.positivityboost.repository.QuoteRepository
import fr.tahia910.android.positivityboost.ui.MainViewModel
import fr.tahia910.android.positivityboost.utils.DOG_RETROFIT_NAME
import fr.tahia910.android.positivityboost.utils.DOG_URL
import fr.tahia910.android.positivityboost.utils.QUOTE_RETROFIT_NAME
import fr.tahia910.android.positivityboost.utils.QUOTE_URL
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
        single(named(DOG_RETROFIT_NAME)) {
            (get() as ApiClient).createDogRetrofitBuilder().baseUrl(DOG_URL).build()
        }
    }

    private val apiModule: Module = module {
        single { (get(named(QUOTE_RETROFIT_NAME)) as Retrofit).create(QuoteApi::class.java) }
        single { (get(named(DOG_RETROFIT_NAME)) as Retrofit).create(DogApi::class.java) }
    }

    private val repositoryModule: Module = module {
        single { QuoteRepository(get()) }
        single { DogRepository(get()) }
    }

    private val viewModelModule: Module = module {
        viewModel { MainViewModel(get(), get()) }
    }
}