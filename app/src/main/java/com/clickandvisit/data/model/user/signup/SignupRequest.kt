package com.clickandvisit.data.model.user.signup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SignupRequest(
    @Json(name = "professionel_particulier")
    val proPar: Int,
    @Json(name = "siret")
    val siret: String,
    @Json(name = "raison_social")
    val rSocial: String,
    @Json(name = "civilite")
    val civility: Int,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "phone_number")
    val phoneNumber: String

)