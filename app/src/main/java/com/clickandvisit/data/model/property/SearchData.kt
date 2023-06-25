package com.clickandvisit.data.model.property

import android.os.Parcelable
import com.clickandvisit.global.utils.getWsNormalDateFormat
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchData(
    @Json(name = "id")
    val id: String,
    @Json(name = "creation_date")
    val creationDate: String,
    @Json(name = "data")
    val data: SearchDataModel
): Parcelable{

    fun getDateWithFormat(): String{
        return getWsNormalDateFormat(creationDate)!!
    }

}
