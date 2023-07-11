package com.clickandvisit.data.model.reservation

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class ReservateModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "user_id")
    val user_id: String,
    @Json(name = "property_id")
    val property_id: String,
    @Json(name = "date_time")
    val date_time: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "date_c")
    val date_c: String,
) : Parcelable {
    override fun toString(): String {
        return "ReservateModel(id='$id', user_id='$user_id', property_id='$property_id', date_time='$date_time', status='$status', date_c='$date_c')"
    }
}