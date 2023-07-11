package com.clickandvisit.data.model.reservation


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ReserveResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "reservations")
    val reservations: List<ReservateModel>
): Parcelable {
    override fun toString(): String {
        return "ReservationResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', reservations=$reservations)"
    }
}