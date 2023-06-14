package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Search(
    @Json(name = "status")
    val status: String,
    @Json(name = "type")
    val type: List<Int>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        TODO("type")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Search(status='$status', type=$type)"
    }

    companion object CREATOR : Parcelable.Creator<Search> {
        override fun createFromParcel(parcel: Parcel): Search {
            return Search(parcel)
        }

        override fun newArray(size: Int): Array<Search?> {
            return arrayOfNulls(size)
        }
    }
}
