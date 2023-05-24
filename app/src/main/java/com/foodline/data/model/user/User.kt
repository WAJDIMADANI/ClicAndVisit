package com.foodline.data.model.user

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @PrimaryKey
    @Json(name = "id")
    val id: String,
    @Json(name = "first_name")
    val firstName: String = "",
    @Json(name = "last_name")
    val lastName: String = "",
    @Json(name = "email")
    val email: String,
    @Json(name = "token")
    val token: String

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(firstName)
        writeString(lastName)
        writeString(email)
        writeString(token)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}