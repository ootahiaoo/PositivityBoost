package com.example.android.positivityboost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuoteItem(
    val affirmation: String
)