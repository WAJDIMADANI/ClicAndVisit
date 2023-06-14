package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SavedSearchResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "data")
    val data: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "SavedSearchResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', data='$data')"
    }

    companion object CREATOR : Parcelable.Creator<SavedSearchResponse> {
        override fun createFromParcel(parcel: Parcel): SavedSearchResponse {
            return SavedSearchResponse(parcel)
        }

        override fun newArray(size: Int): Array<SavedSearchResponse?> {
            return arrayOfNulls(size)
        }
    }
}