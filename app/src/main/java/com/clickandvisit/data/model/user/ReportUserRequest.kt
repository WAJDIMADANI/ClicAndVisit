package com.clickandvisit.data.model.user

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportUserRequest(
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "user_to_report")
    val userRId: Int,
    @Json(name = "message")
    val message: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeInt(userRId)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ReportUserRequest(userId=$userId, userRId=$userRId, message='$message')"
    }

    companion object CREATOR : Parcelable.Creator<ReportUserRequest> {
        override fun createFromParcel(parcel: Parcel): ReportUserRequest {
            return ReportUserRequest(parcel)
        }

        override fun newArray(size: Int): Array<ReportUserRequest?> {
            return arrayOfNulls(size)
        }
    }
}