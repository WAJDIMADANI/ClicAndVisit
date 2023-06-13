package com.clickandvisit.data.model.chat

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "message_author")
    val authorId: Int,
    @Json(name = "message_author_name")
    val authorName: String,
    @Json(name = "message_author_picture")
    val authorPhoto: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "messages_time")
    val time: String,
    @Json(name = "messages_date")
    val date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(authorId)
        parcel.writeString(authorName)
        parcel.writeString(authorPhoto)
        parcel.writeString(message)
        parcel.writeString(time)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Messages(authorId=$authorId, authorName='$authorName', authorPhoto='$authorPhoto', message='$message', time='$time', date='$date')"
    }

    companion object CREATOR : Parcelable.Creator<Message> {
        override fun createFromParcel(parcel: Parcel): Message {
            return Message(parcel)
        }

        override fun newArray(size: Int): Array<Message?> {
            return arrayOfNulls(size)
        }
    }
}