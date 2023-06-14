package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class FavoritesResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "propertys")
    val properties: List<Property>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.createTypedArrayList(Property)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
        parcel.writeTypedList(properties)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "FavoritesResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', properties=$properties)"
    }

    companion object CREATOR : Parcelable.Creator<FavoritesResponse> {
        override fun createFromParcel(parcel: Parcel): FavoritesResponse {
            return FavoritesResponse(parcel)
        }

        override fun newArray(size: Int): Array<FavoritesResponse?> {
            return arrayOfNulls(size)
        }
    }
}