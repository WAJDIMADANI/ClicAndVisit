package com.clickandvisit.data.model.user

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ReportedUser(
    @Json(name = "user_to_report")
    val userToReport: Boolean,
    @Json(name = "message")
    val message: Int,
    @Json(name = "date_report")
    val reportDate: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (userToReport) 1 else 0)
        parcel.writeInt(message)
        parcel.writeString(reportDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ReportedUser(userToReport=$userToReport, message=$message, reportDate='$reportDate')"
    }

    companion object CREATOR : Parcelable.Creator<ReportedUser> {
        override fun createFromParcel(parcel: Parcel): ReportedUser {
            return ReportedUser(parcel)
        }

        override fun newArray(size: Int): Array<ReportedUser?> {
            return arrayOfNulls(size)
        }
    }
}
