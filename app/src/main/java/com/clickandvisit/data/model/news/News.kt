package com.clickandvisit.data.model.news


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class News(
    @Json(name = "title") val title: String,
    @Json(name = "url") val image: String = "https://cataas.com/cat"
)