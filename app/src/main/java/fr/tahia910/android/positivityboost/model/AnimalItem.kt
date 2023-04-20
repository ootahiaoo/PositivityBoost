package fr.tahia910.android.positivityboost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnimalItem(
    val id: String,
    val url: String,
    val height: Int,
    val width: Int
)
