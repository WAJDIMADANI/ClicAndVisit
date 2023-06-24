package com.clickandvisit.data.model.reservation

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ReservationDetails(
    @Json(name = "status")
    val status: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "property_id")
    val propertyId: String,
    @Json(name = "date_time")
    val dateTime: String,
    @Json(name = "date_c")
    val dateC: String
): Parcelable {
    override fun toString(): String {
        return "ReservationDetails(status='$status', statusCode=$statusCode, id='$id', userId='$userId', propertyId='$propertyId', dateTime='$dateTime', dateC='$dateC')"
    }
}
