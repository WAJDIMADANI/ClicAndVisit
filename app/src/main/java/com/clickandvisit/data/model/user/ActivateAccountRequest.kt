package com.clickandvisit.data.model.user

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ActivateAccountRequest(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "code_activation")
    val code: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeString(code)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ActivateAccountRequest(userId=$userId, code='$code')"
    }

    companion object CREATOR : Parcelable.Creator<ActivateAccountRequest> {
        override fun createFromParcel(parcel: Parcel): ActivateAccountRequest {
            return ActivateAccountRequest(parcel)
        }

        override fun newArray(size: Int): Array<ActivateAccountRequest?> {
            return arrayOfNulls(size)
        }
    }
}