package com.example.android.positivityboost.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.positivityboost.model.DogItem
import com.example.android.positivityboost.network.DogApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class DogRepository (private val dogApi: DogApi): KoinComponent {

    fun getDog(scope: CoroutineScope): LiveData<DogItem> {
        val result = MutableLiveData<DogItem>()

        try {
            scope.launch {
                val response = dogApi.getRandomDog()[0]

                result.value = response
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}