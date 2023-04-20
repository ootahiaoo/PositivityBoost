package fr.tahia910.android.positivityboost.network

import fr.tahia910.android.positivityboost.model.AnimalItem
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("images/search")
    suspend fun getRandomCat(
        @Query("size") size: String = "med",
        @Query("mime_types") mimeTypes: List<String> = listOf("jpg"),
        @Query("limit") limit: Int = 1,
    ) : List<AnimalItem>
}
