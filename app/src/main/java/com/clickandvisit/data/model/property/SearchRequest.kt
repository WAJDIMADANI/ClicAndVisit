package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchRequest(

    @Json(name = "type_annonce")
    val adsType: Int?,
    @Json(name = "categorie")
    val category: Int?,

    @Json(name = "min_rooms")
    val minRooms: Int?,
    @Json(name = "max_rooms")
    val maxRooms: Int?,

    @Json(name = "min-area")
    val minArea: Int?,
    @Json(name = "max-area")
    val maxArea: Int?,

    @Json(name = "min-price")
    val minPrice: Int?,
    @Json(name = "max-price")
    val maxPrice: Int?,

    @Json(name = "favorite_user_id")
    val favoriteUserId: Int?,

    @Json(name = "save_search")
    val saveSearch: Int = 0, //0 : No / 1 : Yes

    @Json(name = "user_id")
    val userId: Int?,

    @Json(name = "adresse")
    val address: String?,

    @Json(name = "sortby") // date/price/surface
    val sortBy: String = "date",

    @Json(name = "sorthow") // asc/desc
    val sortHow: String = "desc"

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adsType)
        parcel.writeValue(category)
        parcel.writeValue(minRooms)
        parcel.writeValue(maxRooms)
        parcel.writeValue(minArea)
        parcel.writeValue(maxArea)
        parcel.writeValue(minPrice)
        parcel.writeValue(maxPrice)
        parcel.writeValue(favoriteUserId)
        parcel.writeInt(saveSearch)
        parcel.writeValue(userId)
        parcel.writeString(address)
        parcel.writeString(sortBy)
        parcel.writeString(sortHow)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "SearchRequest(adsType=$adsType, category=$category, minRooms=$minRooms, maxRooms=$maxRooms, minArea=$minArea, maxArea=$maxArea, minPrice=$minPrice, maxPrice=$maxPrice, favoriteUserId=$favoriteUserId, saveSearch=$saveSearch, userId=$userId, address=$address, sortBy='$sortBy', sortHow='$sortHow')"
    }

    companion object CREATOR : Parcelable.Creator<SearchRequest> {
        override fun createFromParcel(parcel: Parcel): SearchRequest {
            return SearchRequest(parcel)
        }

        override fun newArray(size: Int): Array<SearchRequest?> {
            return arrayOfNulls(size)
        }
    }
}
