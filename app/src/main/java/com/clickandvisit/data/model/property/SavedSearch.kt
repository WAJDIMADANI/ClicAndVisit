package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SavedSearch(
    @Json(name = "id")
    val id: Int,
    @Json(name = "creation_date")
    val date: String,
    @Json(name = "data")
    val data: Search
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(Search::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(date)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "SavedSearch(id=$id, date='$date', data=$data)"
    }

    companion object CREATOR : Parcelable.Creator<SavedSearch> {
        override fun createFromParcel(parcel: Parcel): SavedSearch {
            return SavedSearch(parcel)
        }

        override fun newArray(size: Int): Array<SavedSearch?> {
            return arrayOfNulls(size)
        }
    }
}
