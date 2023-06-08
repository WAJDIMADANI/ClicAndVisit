package com.clickandvisit.data.model.property

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchRequest(
    @Json(name = "type_annonce")
    val adsType: Int,
    @Json(name = "categorie")
    val category: Int,

    @Json(name = "min_rooms")
    val minRooms: Int,
    @Json(name = "max_rooms")
    val maxRooms: Int,

    @Json(name = "min-area")
    val minArea: Int,
    @Json(name = "max-area")
    val maxArea: Int,

    @Json(name = "min-price")
    val minPrice: Int,
    @Json(name = "max-price")
    val maxPrice: Int,

    @Json(name = "favorite_user_id")
    val favoriteUserId: Int,

    @Json(name = "save_search")
    val saveSearch: Int, //0 : No / 1 : Yes

    @Json(name = "user_id")
    val userId: Int

)
