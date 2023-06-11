package com.clickandvisit.data.model.user.signup

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SignupRequest(
    @Json(name = "professionel_particulier")
    val proPar: Int,
    @Json(name = "siret")
    val siret: String = "",
    @Json(name = "raison_social")
    val rSocial: String = "",
    @Json(name = "civilite")
    val civility: Int = 0,
    @Json(name = "first_name")
    val firstName: String = "",
    @Json(name = "last_name")
    val lastName: String = "",
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "phone_number")
    val phoneNumber: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(proPar)
        parcel.writeString(siret)
        parcel.writeString(rSocial)
        parcel.writeInt(civility)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(phoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "SignupRequest(proPar=$proPar, siret='$siret', rSocial='$rSocial', civility=$civility, firstName='$firstName', lastName='$lastName', email='$email', password='$password', phoneNumber='$phoneNumber')"
    }

    companion object CREATOR : Parcelable.Creator<SignupRequest> {
        override fun createFromParcel(parcel: Parcel): SignupRequest {
            return SignupRequest(parcel)
        }

        override fun newArray(size: Int): Array<SignupRequest?> {
            return arrayOfNulls(size)
        }
    }
}