package com.clickandvisit.data.model.property.add

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PropertyAddResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "prop_id")
    val propId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
        parcel.writeInt(propId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "PropertyAddResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', propId=$propId)"
    }

    companion object CREATOR : Parcelable.Creator<PropertyAddResponse> {
        override fun createFromParcel(parcel: Parcel): PropertyAddResponse {
            return PropertyAddResponse(parcel)
        }

        override fun newArray(size: Int): Array<PropertyAddResponse?> {
            return arrayOfNulls(size)
        }
    }
}