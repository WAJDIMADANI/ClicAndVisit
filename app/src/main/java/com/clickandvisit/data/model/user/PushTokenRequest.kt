package com.clickandvisit.data.model.user

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushTokenRequest(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "token")
    val token: Int,
    @Json(name = "device")
    val device: String = "android"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeInt(token)
        parcel.writeString(device)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "PushTokenRequest(userId=$userId, token=$token, device='$device')"
    }

    companion object CREATOR : Parcelable.Creator<PushTokenRequest> {
        override fun createFromParcel(parcel: Parcel): PushTokenRequest {
            return PushTokenRequest(parcel)
        }

        override fun newArray(size: Int): Array<PushTokenRequest?> {
            return arrayOfNulls(size)
        }
    }
}