package com.clickandvisit.data.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportUserRequest(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "user_to_report")
    val userRId: Int,
    @Json(name = "message")
    val message: String
)