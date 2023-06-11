package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class FavoriteRequest(
    @Json(name = "property_id")
    val email: Int,
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "action")
    val action: String // "add" or "remove"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(email)
        parcel.writeInt(userId)
        parcel.writeString(action)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "FavoriteRequest(email=$email, userId=$userId, action='$action')"
    }

    companion object CREATOR : Parcelable.Creator<FavoriteRequest> {
        override fun createFromParcel(parcel: Parcel): FavoriteRequest {
            return FavoriteRequest(parcel)
        }

        override fun newArray(size: Int): Array<FavoriteRequest?> {
            return arrayOfNulls(size)
        }
    }

}