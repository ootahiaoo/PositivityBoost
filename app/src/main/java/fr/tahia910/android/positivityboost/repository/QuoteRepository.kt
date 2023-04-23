package fr.tahia910.android.positivityboost.repository

import fr.tahia910.android.positivityboost.local.DataStorePreferences
import fr.tahia910.android.positivityboost.model.SettingsLanguage
import fr.tahia910.android.positivityboost.model.asResult
import fr.tahia910.android.positivityboost.network.QuoteApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class QuoteRepository(
    private val quoteApi: QuoteApi,
    private val dataStorePreferences: DataStorePreferences
) : KoinComponent {

    private val preferredLanguage = dataStorePreferences.getPreferredLanguage()

    private fun getEnglishQuote() = flow { emit(quoteApi.getQuote().affirmation) }

    @OptIn(FlowPreview::class)
    val quoteStream = preferredLanguage.flatMapMerge { language ->
        if (language == SettingsLanguage.JAPANESE) {
            // get hardcoded string
            flow { emit("") }
        } else {
            getEnglishQuote()
        }
    }.asResult()
}
