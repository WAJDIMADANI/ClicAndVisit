package com.clickandvisit.data.model.user

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ReportUserResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "reported_users")
    val reportedUsers: ReportedUser,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(ReportedUser::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
        parcel.writeParcelable(reportedUsers, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ReportUserResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', reportedUsers=$reportedUsers)"
    }

    companion object CREATOR : Parcelable.Creator<ReportUserResponse> {
        override fun createFromParcel(parcel: Parcel): ReportUserResponse {
            return ReportUserResponse(parcel)
        }

        override fun newArray(size: Int): Array<ReportUserResponse?> {
            return arrayOfNulls(size)
        }
    }
}