package com.clickandvisit.data.model.property

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PropertyOwner(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "photo")
    val photo: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "professionel_particulier")
    val proPar: String
)