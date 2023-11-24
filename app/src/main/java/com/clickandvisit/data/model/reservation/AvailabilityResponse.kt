package com.clickandvisit.data.model.reservation

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AvailabilityResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "available_hours")
    val availableHours: List<List<String?>?>,
    @Json(name = "execution_duration")
    val executionDuration: String
) : Parcelable {

    override fun toString(): String {
        return "AvailabilityResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', availableHours=$availableHours)"
    }

}