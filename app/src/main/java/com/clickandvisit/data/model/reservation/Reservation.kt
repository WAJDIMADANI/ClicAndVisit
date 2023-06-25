package com.clickandvisit.data.model.reservation

import android.os.Parcelable
import com.clickandvisit.data.model.property.Details
import com.clickandvisit.data.model.property.PropertyOwner
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class Reservation(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "category")
    val category: String,
    @Json(name = "album")
    val album: List<String>,
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
    val price: String,
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
    @Json(name = "reservation")
    val reservationDetails: ReservationDetails?,
    @Json(name = "reservation_user")
    val reservationUser: ReservationUser?
): Parcelable {
    override fun toString(): String {
        return "Reservation(id=$id, title='$title', type='$type', category='$category', album=$album, mainPhoto=$mainPhoto, status='$status', statusCode=$statusCode, visitNow=$visitNow, owner=$owner, isFavorite=$isFavorite, surface='$surface', price='$price', stage='$stage', stageS='$stageS', energy='$energy', ges='$ges', otherDetails='$otherDetails', city='$city', postalCode='$postalCode', road='$road', lat='$lat', long='$long', interphone='$interphone', portail='$portail', otherInfo='$otherInfo', details=$details, reservationDetails=$reservationDetails, reservationUser=$reservationUser)"
    }
}