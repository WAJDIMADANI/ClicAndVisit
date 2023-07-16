package com.clickandvisit.data.model.reservation

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ReservationAccept(
    @Json(name = "id")
    val id: String,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "property_id")
    val propertyId: String,
    @Json(name = "date_time")
    val dateTime: String,
    @Json(name = "status")
    val status: Int,
    @Json(name = "date_c")
    val dateC: String
): Parcelable {
    override fun toString(): String {
        return "ReservationAccept(id='$id', userId='$userId', propertyId='$propertyId', dateTime='$dateTime', status=$status, dateC='$dateC')"
    }
}