package fr.tahia910.android.positivityboost.network

import fr.tahia910.android.positivityboost.model.DogItem
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