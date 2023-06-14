package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Owner(

    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "photo")
    val photo: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "professionel_particulier")
    val proPar: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(photo)
        parcel.writeString(phone)
        parcel.writeString(proPar)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Owner(id='$id', name='$name', photo='$photo', phone='$phone', proPar='$proPar')"
    }

    companion object CREATOR : Parcelable.Creator<Owner> {
        override fun createFromParcel(parcel: Parcel): Owner {
            return Owner(parcel)
        }

        override fun newArray(size: Int): Array<Owner?> {
            return arrayOfNulls(size)
        }
    }
}