package com.clickandvisit.data.model.chat

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ContactOwnerResponse(
    @Json(name = "result")
    val result: Boolean,
    @Json(name = "result_code")
    val resultCode: Int,
    @Json(name = "result_message")
    val resultMsg: String,
    @Json(name = "message_id")
    val messageId: Int,
    @Json(name = "discution_id")
    val discussionId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (result) 1 else 0)
        parcel.writeInt(resultCode)
        parcel.writeString(resultMsg)
        parcel.writeInt(messageId)
        parcel.writeInt(discussionId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ContactOwnerResponse(result=$result, resultCode=$resultCode, resultMsg='$resultMsg', messageId=$messageId, discussionId=$discussionId)"
    }

    companion object CREATOR : Parcelable.Creator<ContactOwnerResponse> {
        override fun createFromParcel(parcel: Parcel): ContactOwnerResponse {
            return ContactOwnerResponse(parcel)
        }

        override fun newArray(size: Int): Array<ContactOwnerResponse?> {
            return arrayOfNulls(size)
        }
    }
}