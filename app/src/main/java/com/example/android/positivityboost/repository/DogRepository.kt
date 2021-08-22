package com.example.android.positivityboost.repository

import com.example.android.positivityboost.network.DogApi
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class DogRepository(private val dogApi: DogApi) : KoinComponent {

    fun getRandomDog() = flow { emit(dogApi.getRandomDog()[0]) }
}