package com.clickandvisit.data.model.chat

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class DiscussionsResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "disuctions")
    val discussions: List<Discussion>

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.createTypedArrayList(Discussion)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
        parcel.writeTypedList(discussions)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "DiscussionsResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', discussions=$discussions)"
    }

    companion object CREATOR : Parcelable.Creator<DiscussionsResponse> {
        override fun createFromParcel(parcel: Parcel): DiscussionsResponse {
            return DiscussionsResponse(parcel)
        }

        override fun newArray(size: Int): Array<DiscussionsResponse?> {
            return arrayOfNulls(size)
        }
    }
}