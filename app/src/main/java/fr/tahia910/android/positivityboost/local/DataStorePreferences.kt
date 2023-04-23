package fr.tahia910.android.positivityboost.local

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import fr.tahia910.android.positivityboost.model.AnimalType
import fr.tahia910.android.positivityboost.model.SettingsLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class DataStorePreferences(private val context: Context) {

    suspend fun setDisplayDogs(isFilterEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.KEY_DISPLAY_DOGS] = isFilterEnabled
        }
    }

    suspend fun setDisplayCats(isFilterEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.KEY_DISPLAY_CATS] = isFilterEnabled
        }
    }

    fun getAnimalImageFilters(): Flow<List<AnimalType>> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Default filter is dogs
            val isDisplayingDogs = preferences[DataStoreKeys.KEY_DISPLAY_DOGS] ?: true
            val isDisplayingCats = preferences[DataStoreKeys.KEY_DISPLAY_CATS] ?: false

            val list = mutableListOf<AnimalType>()
            if (isDisplayingDogs) list.add(AnimalType.DOG)
            if (isDisplayingCats) list.add(AnimalType.CAT)
            list
        }

    suspend fun setPreferredLanguage(language: SettingsLanguage) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.KEY_PREFERRED_LANGUAGE] = language.name
        }
    }

    fun getPreferredLanguage(): Flow<SettingsLanguage> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val language = preferences[DataStoreKeys.KEY_PREFERRED_LANGUAGE]
                    ?: SettingsLanguage.ENGLISH.name

                when (language) {
                    SettingsLanguage.JAPANESE.name -> SettingsLanguage.JAPANESE
                    else -> SettingsLanguage.ENGLISH
                }
            }

    private object DataStoreKeys {
        val KEY_DISPLAY_DOGS = booleanPreferencesKey("KEY_DISPLAY_DOGS")
        val KEY_DISPLAY_CATS = booleanPreferencesKey("KEY_DISPLAY_CATS")
        val KEY_PREFERRED_LANGUAGE = stringPreferencesKey("KEY_PREFERRED_LANGUAGE")
    }
}
