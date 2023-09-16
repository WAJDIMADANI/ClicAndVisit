package com.clickandvisit.data.model.user.signup

import android.os.Parcel
import android.os.Parcelable
import com.clickandvisit.data.model.user.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignupResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "user")
    val user: User?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(User::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "SignupResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', user=$user)"
    }

    companion object CREATOR : Parcelable.Creator<SignupResponse> {
        override fun createFromParcel(parcel: Parcel): SignupResponse {
            return SignupResponse(parcel)
        }

        override fun newArray(size: Int): Array<SignupResponse?> {
            return arrayOfNulls(size)
        }
    }
}