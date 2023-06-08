package com.clickandvisit.data.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ActivateAccountRequest(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "code_activation")
    val code: String
)