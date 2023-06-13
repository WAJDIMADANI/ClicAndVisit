package com.clickandvisit.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GlobalResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "GlobalResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg')"
    }

    companion object CREATOR : Parcelable.Creator<GlobalResponse> {
        override fun createFromParcel(parcel: Parcel): GlobalResponse {
            return GlobalResponse(parcel)
        }

        override fun newArray(size: Int): Array<GlobalResponse?> {
            return arrayOfNulls(size)
        }
    }

}