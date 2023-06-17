package com.clickandvisit.data.model.chat

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Discussion(
    @Json(name = "discution_id")
    val discId: String,
    @Json(name = "from")
    val from: String,
    @Json(name = "from_name")
    val fromName: String,
    @Json(name = "from_picture")
    val fromPicture: String,
    @Json(name = "property_id")
    val propertyId: String,
    @Json(name = "property")
    val property: String,
    @Json(name = "last_message")
    val lastMessage: String,
    @Json(name = "date")
    val date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(discId)
        parcel.writeString(from)
        parcel.writeString(fromName)
        parcel.writeString(fromPicture)
        parcel.writeString(propertyId)
        parcel.writeString(property)
        parcel.writeString(lastMessage)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Discussion(discId='$discId', from='$from', fromName='$fromName', fromPicture='$fromPicture', propertyId='$propertyId', property='$property', lastMessage='$lastMessage', date='$date')"
    }

    companion object CREATOR : Parcelable.Creator<Discussion> {
        override fun createFromParcel(parcel: Parcel): Discussion {
            return Discussion(parcel)
        }

        override fun newArray(size: Int): Array<Discussion?> {
            return arrayOfNulls(size)
        }
    }
}