package com.clickandvisit.data.model.property

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class FavoriteRequest(
    @Json(name = "property_id")
    val email: Int,
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "action")
    val action: String // "add" or "remove"
)