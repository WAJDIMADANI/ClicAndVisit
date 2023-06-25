package com.clickandvisit.data.model.property

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchDataModel(
    @Json(name = "status")
    val status: String?,
    @Json(name = "adresse")
    val adresse: String?,
    @Json(name = "min_rooms")
    val minRooms: String?,
    @Json(name = "max_rooms")
    val maxRooms: String?,
    @Json(name = "type")
    val type: List<Int>?,
    @Json(name = "min-area")
    val minArea: String?,
    @Json(name = "max-area")
    val maxArea: String?,
    @Json(name = "min-price")
    val minPrice: String?,
    @Json(name = "max-price")
    val maxPrice: String?
) : Parcelable {

    fun getAddress(): String {
        return if (adresse.isNullOrEmpty()) {
            "Toute la France"
        } else {
            adresse
        }
    }

    fun getPrice(): String {
        return if (minPrice.isNullOrEmpty() && maxPrice.isNullOrEmpty()) {
            "PRIX NON\nPRÉCISÉ"
        } else if (minPrice.isNullOrEmpty() && maxPrice.isNullOrEmpty().not()) {
            "MAXIMUM\n$maxPrice €"
        } else if (minPrice.isNullOrEmpty().not() && maxPrice.isNullOrEmpty()) {
            "MINIMUM\n$minPrice €"
        } else {
            "ENTRE\n$minPrice €\nET\n$maxPrice €"
        }
    }

    fun getRoomsNbr(): String {
        return if (minRooms.isNullOrEmpty() && maxRooms.isNullOrEmpty()) {
            "NOMBRE DE\nPIÈCES NON\nPRÉCISÉ"
        } else if (minRooms.isNullOrEmpty() && maxRooms.isNullOrEmpty().not()) {
            return try {
                "MAXIMUM\nT${maxRooms!!.toInt().plus(1)}"
            } catch (e: NumberFormatException) {
                "MAXIMUM T6 et +"
            }
        } else if (minRooms.isNullOrEmpty().not() && maxRooms.isNullOrEmpty()) {
            "MINIMUM\nT${minRooms!!.toInt().plus(1)}"
        } else {
            return try {
                "ENTRE \nT${minRooms!!.toInt().plus(1)}\nET\nT${maxRooms!!.toInt().plus(1)}"
            } catch (e: NumberFormatException) {
                "ENTRE \nT${minRooms!!.toInt().plus(1)}\nET\nT6 et +"
            }

        }
    }

    fun getArea(): String {
        return if (minArea.isNullOrEmpty() && maxArea.isNullOrEmpty()) {
            "SURFACE\nNON PRÉCISÉ"
        } else if (minArea.isNullOrEmpty() && maxArea.isNullOrEmpty().not()) {
            "MAXIMUM\n$maxArea m²"
        } else if (minArea.isNullOrEmpty().not() && maxArea.isNullOrEmpty()) {
            "MINIMUM\n$minArea m²"
        } else {
            "ENTRE\n$minArea m²\nET\n$maxArea m²"
        }
    }


    fun getStringTypesById(): String {
        if (type.isNullOrEmpty()) {
            return "Tout"
        } else {
            var typesList = ""
            type.forEach {
                when (it) {
                    96 -> {
                        typesList = typesList.plus(" Maison,")
                    }
                    97 -> {
                        typesList = typesList.plus(" Appartement,")
                    }
                    98 -> {
                        typesList = typesList.plus(" Garage,")
                    }
                    99 -> {
                        typesList = typesList.plus(" Bureau,")
                    }
                    100 -> {
                        typesList = typesList.plus(" Terrain,")
                    }
                    101 -> {
                        typesList = typesList.plus(" Commerce,")
                    }
                }
            }
            return typesList.substring(1, typesList.length - 1)
        }

    }
}
