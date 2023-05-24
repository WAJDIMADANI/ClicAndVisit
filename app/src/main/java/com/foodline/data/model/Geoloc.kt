package com.foodline.data.model

import com.squareup.moshi.Json

data class Geoloc(
    @Json(name = "lat")
    val lat: Float,
    @Json(name = "lng")
    val lng: Float
)