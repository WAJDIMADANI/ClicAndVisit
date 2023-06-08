package com.clickandvisit.data.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushTokenRequest(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "token")
    val token: Int,
    @Json(name = "device")
    val device: String = "android"
)