package com.clickandvisit.data.model.user

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id")
    val id: String,

    @Json(name = "email")
    var email: String,

    @Json(name = "first_name")
    var firstName: String = "",

    @Json(name = "last_name")
    var lastName: String = "",

    @Json(name = "professionel_particulier")
    val proPar: String = "",

    @Json(name = "civilite")
    var civility: String,

    @Json(name = "phone_number")
    var phoneNumber: String,

    @Json(name = "siret")
    var siret: String,

    @Json(name = "raison_social")
    var rSocial: String,

    @Json(name = "profile_photo")
    var photo: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    fun isPro(): Boolean {
        return proPar.toInt() == 1
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(email)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(proPar)
        parcel.writeString(civility)
        parcel.writeString(phoneNumber)
        parcel.writeString(siret)
        parcel.writeString(rSocial)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "User(id='$id', email='$email', firstName='$firstName', lastName='$lastName', proPar='$proPar', civility='$civility', phoneNumber='$phoneNumber', siret='$siret', rSocial='$rSocial', photo='$photo')"
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}