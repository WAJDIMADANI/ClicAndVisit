package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PropertyDetailsResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "property")
    val property: Property
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(Property::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
        parcel.writeParcelable(property, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "PropertyDetailsResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', property=$property)"
    }

    companion object CREATOR : Parcelable.Creator<PropertyDetailsResponse> {
        override fun createFromParcel(parcel: Parcel): PropertyDetailsResponse {
            return PropertyDetailsResponse(parcel)
        }

        override fun newArray(size: Int): Array<PropertyDetailsResponse?> {
            return arrayOfNulls(size)
        }
    }

}