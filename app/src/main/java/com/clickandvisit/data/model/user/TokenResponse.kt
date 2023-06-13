package com.clickandvisit.data.model.user


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class TokenResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "token")
    val token: Int,
    @Json(name = "device")
    val device: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(token)
        parcel.writeString(device)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "TokenResponse(result=$result, token=$token, device='$device')"
    }

    companion object CREATOR : Parcelable.Creator<TokenResponse> {
        override fun createFromParcel(parcel: Parcel): TokenResponse {
            return TokenResponse(parcel)
        }

        override fun newArray(size: Int): Array<TokenResponse?> {
            return arrayOfNulls(size)
        }
    }
}