package com.clickandvisit.data.model.property

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Details(
    @Json(name = "chambres")
    val chambres: String,
    @Json(name = "pieces")
    val pieces: String?,
    @Json(name = "suites")
    val suites: String,
    @Json(name = "salles_de_bains")
    val sallesDeBain: String,
    @Json(name = "salles_d_eau")
    val sallesDeau: String,
    @Json(name = "bureaux")
    val bureaux: String,

    @Json(name = "dressing")
    val dressing: String,
    @Json(name = "garages")
    val garages: String,
    @Json(name = "caves")
    val caves: String,
    @Json(name = "balcons")
    val balcons: String,
    @Json(name = "terrasse")
    val terrasse: String,

    @Json(name = "surface_terrain")
    val surfaceTerrain: String,
    @Json(name = "annee")
    val annee: String,
    @Json(name = "piscine")
    val piscine: String,
    @Json(name = "piscinable")
    val piscinable: String,
    @Json(name = "pool_house")
    val poolHouse: String,

    @Json(name = "sans_vis_a_vis")
    val sansVisAVis: String,
    @Json(name = "ascenseur")
    val ascenseur: String,
    @Json(name = "duplex")
    val duplex: String,
    @Json(name = "triplex")
    val triplex: String,
    @Json(name = "rez_de_jardin")
    val rezDeJardin: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
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
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(chambres)
        parcel.writeString(pieces)
        parcel.writeString(suites)
        parcel.writeString(sallesDeBain)
        parcel.writeString(sallesDeau)
        parcel.writeString(bureaux)
        parcel.writeString(dressing)
        parcel.writeString(garages)
        parcel.writeString(caves)
        parcel.writeString(balcons)
        parcel.writeString(terrasse)
        parcel.writeString(surfaceTerrain)
        parcel.writeString(annee)
        parcel.writeString(piscine)
        parcel.writeString(piscinable)
        parcel.writeString(poolHouse)
        parcel.writeString(sansVisAVis)
        parcel.writeString(ascenseur)
        parcel.writeString(duplex)
        parcel.writeString(triplex)
        parcel.writeString(rezDeJardin)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Details> {
        override fun createFromParcel(parcel: Parcel): Details {
            return Details(parcel)
        }

        override fun newArray(size: Int): Array<Details?> {
            return arrayOfNulls(size)
        }
    }


    fun getTPieces(): String {
        return if (pieces.isNullOrEmpty()){
            "T0"
        }else{
            try {
                "T$pieces"
            } catch (e: NumberFormatException) {
                "T0"
            }
        }
    }
    fun getT_Pieces(): String {
        return if (pieces.isNullOrEmpty()){
            "T0 - "
        }else{
            try {
                "T$pieces - "
            } catch (e: NumberFormatException) {
                "T0 - "
            }
        }
    }

    override fun toString(): String {
        return "Details(chambres='$chambres', pieces=$pieces, suites='$suites', sallesDeBain='$sallesDeBain', sallesDeau='$sallesDeau', bureaux='$bureaux', dressing='$dressing', garages='$garages', caves='$caves', balcons='$balcons', terrasse='$terrasse', surfaceTerrain='$surfaceTerrain', annee='$annee', piscine='$piscine', piscinable='$piscinable', poolHouse='$poolHouse', sansVisAVis='$sansVisAVis', ascenseur='$ascenseur', duplex='$duplex', triplex='$triplex', rezDeJardin=$rezDeJardin)"
    }

}