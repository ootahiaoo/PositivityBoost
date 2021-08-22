package com.example.android.positivityboost.network

import com.example.android.positivityboost.model.DogItem
import retrofit2.http.GET
import retrofit2.http.Query

interface DogApi {

    @GET("images/search")
    suspend fun getRandomDog(
        @Query("size") size: String = "med",
        @Query("mime_types") mimeTypes: List<String> = listOf("jpg"),
        @Query("limit") limit: Int = 1,
    ) : List<DogItem>
}