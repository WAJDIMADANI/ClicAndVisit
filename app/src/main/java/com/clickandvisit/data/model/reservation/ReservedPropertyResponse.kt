package com.clickandvisit.data.model.reservation

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReservedPropertyResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "not_available_hours")
    val availableHours: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
        parcel.writeStringList(availableHours)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReservedPropertyResponse> {
        override fun createFromParcel(parcel: Parcel): ReservedPropertyResponse {
            return ReservedPropertyResponse(parcel)
        }

        override fun newArray(size: Int): Array<ReservedPropertyResponse?> {
            return arrayOfNulls(size)
        }
    }
}