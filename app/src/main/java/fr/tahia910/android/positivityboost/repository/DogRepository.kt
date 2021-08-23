package fr.tahia910.android.positivityboost.repository

import fr.tahia910.android.positivityboost.network.DogApi
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class DogRepository(private val dogApi: DogApi) : KoinComponent {

    fun getRandomDog() = flow { emit(dogApi.getRandomDog()) }
}