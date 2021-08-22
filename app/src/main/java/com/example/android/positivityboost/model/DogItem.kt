package com.example.android.positivityboost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogItem(
    val id: String,
    val url: String,
    val height: Int,
    val width: Int
)