package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.lang.NumberFormatException

@JsonClass(generateAdapter = true)
data class Property(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "category")
    val category: String,
    @Json(name = "album")
    val album: List<String>?,
    @Json(name = "photo_principale")
    val mainPhoto: String?,
    @Json(name = "status")
    val status: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "visit_now")
    val visitNow: Boolean,
    @Json(name = "owner")
    val owner: PropertyOwner,
    @Json(name = "is_favorite")
    var isFavorite: Boolean,
    @Json(name = "surface")
    val surface: String,
    @Json(name = "price")
    var price: String,
    @Json(name = "etage")
    val stage: String,
    @Json(name = "etage_sur")
    val stageS: String,
    @Json(name = "energy")
    val energy: String,
    @Json(name = "ges")
    val ges: String,
    @Json(name = "autre_details")
    val otherDetails: String,
    @Json(name = "ville")
    val city: String,
    @Json(name = "code_postal")
    val postalCode: String,
    @Json(name = "rue")
    val road: String,
    @Json(name = "lat")
    val lat: String,
    @Json(name = "long")
    val long: String,
    @Json(name = "interphone")
    val interphone: String,
    @Json(name = "portail")
    val portail: String,
    @Json(name = "autres_infos")
    val otherInfo: String,
    @Json(name = "details")
    val details: Details,
    var availableHours: List<String>?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(PropertyOwner::class.java.classLoader)!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(Details::class.java.classLoader)!!,
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeString(category)
        parcel.writeStringList(album)
        parcel.writeString(mainPhoto)
        parcel.writeString(status)
        parcel.writeInt(statusCode)
        parcel.writeByte(if (visitNow) 1 else 0)
        parcel.writeParcelable(owner, flags)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeString(surface)
        parcel.writeString(price)
        parcel.writeString(stage)
        parcel.writeString(stageS)
        parcel.writeString(energy)
        parcel.writeString(ges)
        parcel.writeString(otherDetails)
        parcel.writeString(city)
        parcel.writeString(postalCode)
        parcel.writeString(road)
        parcel.writeString(lat)
        parcel.writeString(long)
        parcel.writeString(interphone)
        parcel.writeString(portail)
        parcel.writeString(otherInfo)
        parcel.writeParcelable(details, flags)
        parcel.writeStringList(availableHours)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Property> {
        override fun createFromParcel(parcel: Parcel): Property {
            return Property(parcel)
        }

        override fun newArray(size: Int): Array<Property?> {
            return arrayOfNulls(size)
        }
    }


    fun getPriceNBR(): String {
        return if (price.isEmpty()) {
            ""
        } else {
            try {
                if (price.length >= 3) {
                    price = price.replaceRange(
                        price.length - 3,
                        price.length,
                        " " + price.subSequence(price.length - 3, price.length)
                    )
                }
                "$price €"
            } catch (e: NumberFormatException) {
                ""
            }
        }
    }

    fun getCategories(): String {
        return try {
            "$category - "
        } catch (e: NumberFormatException) {
            " "
        }
    }

    override fun toString(): String {
        return "Property(id=$id, title='$title', type='$type', category='$category', album=$album, mainPhoto=$mainPhoto, status='$status', statusCode=$statusCode, visitNow=$visitNow, owner=$owner, isFavorite=$isFavorite, surface='$surface', price='$price', stage='$stage', stageS='$stageS', energy='$energy', ges='$ges', otherDetails='$otherDetails', city='$city', postalCode='$postalCode', road='$road', lat='$lat', long='$long', interphone='$interphone', portail='$portail', otherInfo='$otherInfo', details=$details)"
    }
}