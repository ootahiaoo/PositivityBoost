package fr.tahia910.android.positivityboost.repository

import fr.tahia910.android.positivityboost.local.DataStorePreferences
import fr.tahia910.android.positivityboost.model.AnimalItem
import fr.tahia910.android.positivityboost.model.AnimalType
import fr.tahia910.android.positivityboost.model.Result
import fr.tahia910.android.positivityboost.model.asResult
import fr.tahia910.android.positivityboost.network.CatApi
import fr.tahia910.android.positivityboost.network.DogApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent

class AnimalRepository(
    private val dogApi: DogApi,
    private val catApi: CatApi,
    private val dataStorePreferences: DataStorePreferences
) : KoinComponent {

    private fun getRandomDog() = flow { emit(dogApi.getRandomDog()) }

    private fun getRandomCat() = flow { emit(catApi.getRandomCat()) }

    private fun getAnimalImageFilters() = dataStorePreferences.getAnimalImageFilters()

    suspend fun getAnimal(): Flow<Result<AnimalItem?>> {
        val imageFilterList = getAnimalImageFilters().first()
        return when (imageFilterList.random()) {
            AnimalType.DOG -> getRandomDog()
            AnimalType.CAT -> getRandomCat()
            else -> getRandomDog()
        }.map { list ->
            list.firstOrNull()
        }.asResult()
    }
}
