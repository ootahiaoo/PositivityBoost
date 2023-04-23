package fr.tahia910.android.positivityboost.repository

import fr.tahia910.android.positivityboost.local.DataStorePreferences
import fr.tahia910.android.positivityboost.model.AnimalType
import fr.tahia910.android.positivityboost.model.SettingsLanguage
import kotlinx.coroutines.flow.combine
import org.koin.core.component.KoinComponent

class SettingsRepository(private val dataStorePreferences: DataStorePreferences) : KoinComponent {

    private val animalImageFilters = dataStorePreferences.getAnimalImageFilters()

    suspend fun setAnimalImageFilters(filterList: List<AnimalType>) {
        dataStorePreferences.setDisplayDogs(filterList.contains(AnimalType.DOG))
        dataStorePreferences.setDisplayCats(filterList.contains(AnimalType.CAT))
    }

    private val preferredLanguage = dataStorePreferences.getPreferredLanguage()

    suspend fun setPreferredLanguage(language: SettingsLanguage) =
        dataStorePreferences.setPreferredLanguage(language = language)

    val userSettingsStream = animalImageFilters.combine(preferredLanguage) { filters, language ->
        Pair(filters, language)
    }
}
