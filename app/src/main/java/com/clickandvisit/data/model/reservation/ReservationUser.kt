package com.clickandvisit.data.model.reservation

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ReservationUser(
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "user_name")
    val userName: String,
    @Json(name = "user_phone")
    val userPhone: String,
    @Json(name = "profile_photo")
    val profilePhoto: String
): Parcelable {
    override fun toString(): String {
        return "ReservationUser(userId='$userId', userName='$userName', userPhone='$userPhone', profilePhoto='$profilePhoto')"
    }
}
