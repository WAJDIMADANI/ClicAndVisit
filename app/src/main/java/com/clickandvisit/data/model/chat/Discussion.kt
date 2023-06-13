package com.clickandvisit.data.model.chat

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Discussion(
    @Json(name = "discution_id")
    val discution_id: String,
    @Json(name = "from")
    val from: String,
    @Json(name = "from_name")
    val from_name: String,
    @Json(name = "from_picture")
    val from_picture: String,
    @Json(name = "property_id")
    val property_id: String,
    @Json(name = "property")
    val property: String,
    @Json(name = "last_message")
    val last_message: String,
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
        parcel.writeString(discution_id)
        parcel.writeString(from)
        parcel.writeString(from_name)
        parcel.writeString(from_picture)
        parcel.writeString(property_id)
        parcel.writeString(property)
        parcel.writeString(last_message)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Discussion(discution_id='$discution_id', from='$from', from_name='$from_name', from_picture='$from_picture', property_id='$property_id', property='$property', last_message='$last_message', date='$date')"
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