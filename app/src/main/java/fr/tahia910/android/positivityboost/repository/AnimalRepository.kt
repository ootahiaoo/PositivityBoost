package fr.tahia910.android.positivityboost.repository

import fr.tahia910.android.positivityboost.network.CatApi
import fr.tahia910.android.positivityboost.network.DogApi
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class AnimalRepository(private val dogApi: DogApi, private val catApi: CatApi) : KoinComponent {

    fun getRandomDog() = flow { emit(dogApi.getRandomDog()) }

    fun getRandomCat() = flow { emit(catApi.getRandomCat()) }
}
