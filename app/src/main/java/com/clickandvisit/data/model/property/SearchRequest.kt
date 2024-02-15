package com.clickandvisit.data.model.property

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class SearchRequest(

    @Json(name = "type_annonce")
    var adsType: Int? = null,// 30 ou 29

    @Json(name = "categorie")
    var category: List<Int>? = listOf(),// 96 -> 101

    @Json(name = "min_rooms")
    var minRooms: String? = null,
    @Json(name = "max_rooms")
    var maxRooms: String? = null,

    @Json(name = "min-area")
    var minArea: Int? = null,
    @Json(name = "max-area")
    var maxArea: Int? = null,

    @Json(name = "min-price")
    var minPrice: String? = null,
    @Json(name = "max-price")
    var maxPrice: String? = null,

    @Json(name = "favorite_user_id")
    var favoriteUserId: Int? = null,

    @Json(name = "save_search")
    var saveSearch: Int? = null, //0 : No / 1 : Yes

    @Json(name = "saved_search_user")
    var savedSearchUser: Int? = null, //user id

    @Json(name = "user_id")
    var userId: Int? = null,

    @Json(name = "adresse")
    var address: String? = null,

    @Json(name = "sortby") // date/price/surface
    var sortBy: String = "date",

    @Json(name = "sorthow") // asc/desc
    var sortHow: String = "desc"

) : Parcelable {
    override fun toString(): String {
        return "SearchRequest(adsType=$adsType, category=$category, minRooms=$minRooms, maxRooms=$maxRooms, minArea=$minArea, maxArea=$maxArea, minPrice=$minPrice, maxPrice=$maxPrice, favoriteUserId=$favoriteUserId, saveSearch=$saveSearch, savedSearchUser=$savedSearchUser, userId=$userId, address=$address, sortBy='$sortBy', sortHow='$sortHow')"
    }
}
