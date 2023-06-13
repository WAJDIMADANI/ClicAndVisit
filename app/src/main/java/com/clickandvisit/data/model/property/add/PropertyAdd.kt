package com.clickandvisit.data.model.property.add

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PropertyAdd(

    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "_logement_id")
    val propId: Int?, // only for edit

    @Json(name = "prop_type")
    val propType: Int,
    @Json(name = "prop_category")
    val propCategory: Int,
    @Json(name = "prop_surface")
    val propSurface: Int,
    @Json(name = "prop_prix")
    val propPrix: Int,
    @Json(name = "prop_etage")
    val propEtage: Int,
    @Json(name = "prop_etage_sur")
    val propEtageSur: Int,
    @Json(name = "prop_enery")
    val propEnery: Int,
    @Json(name = "prop_ges")
    val propGes: Int,
    @Json(name = "prop_infos_complementaires")
    val infoComp: String,
    @Json(name = "prop_meta_chambres")
    val prop_meta_chambres: String,
    @Json(name = "prop_meta_suites")
    val prop_meta_suites: String,
    @Json(name = "prop_meta_salles_de_bains")
    val prop_meta_salles_de_bains: String,
    @Json(name = "prop_meta_salles_d_eau")
    val prop_meta_salles_d_eau: String,
    @Json(name = "prop_meta_bureaux")
    val prop_meta_bureaux: String,
    @Json(name = "prop_meta_dressing")
    val prop_meta_dressing: String,
    @Json(name = "prop_meta_garages")
    val prop_meta_garages: String,
    @Json(name = "prop_meta_caves")
    val prop_meta_caves: String,
    @Json(name = "prop_meta_balcons")
    val prop_meta_balcons: String,
    @Json(name = "prop_meta_terrasse")
    val prop_meta_terrasse: String,
    @Json(name = "prop_meta_surface_terrain")
    val prop_meta_surface_terrain: String,
    @Json(name = "prop_meta_annee")
    val prop_meta_annee: String,
    @Json(name = "prop_meta_piscine")
    val prop_meta_piscine: String,
    @Json(name = "prop_meta_piscinable")
    val prop_meta_piscinable: String,
    @Json(name = "prop_meta_pool_house")
    val prop_meta_pool_house: String,
    @Json(name = "prop_meta_sans_vis_a_vis")
    val prop_meta_sans_vis_a_vis: String,
    @Json(name = "prop_meta_ascenseur")
    val prop_meta_ascenseur: String,
    @Json(name = "prop_meta_duplex")
    val prop_meta_duplex: String,
    @Json(name = "prop_meta_triplex")
    val prop_meta_triplex: String,
    @Json(name = "prop_meta_rez_de_jardin")
    val prop_meta_rez_de_jardin: String,
    @Json(name = "prop_localisation_ville")
    val prop_localisation_ville: String,
    @Json(name = "prop_localisation_codepostal")
    val prop_localisation_codepostal: Int,
    @Json(name = "prop_localisation_complement_adresse")
    val prop_localisation_complement_adresse: String,
    @Json(name = "prop_nom_interphone")
    val proInterphoneName: String,
    @Json(name = "prop_codeportail")
    val propCodeportail: String,
    @Json(name = "prop_autres_informations")
    val propInfos: String,

    // photo
    @Json(name = "prop_main_photo")
    val propMainPhoto: String,
    @Json(name = "prop_album_photo[]")
    val propAlbumPhoto1: String,
    @Json(name = "prop_album_photo[]")
    val propAlbumPhoto2: String,
    @Json(name = "prop_album_photo[]")
    val propAlbumPhoto3: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
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
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeValue(propId)
        parcel.writeInt(propType)
        parcel.writeInt(propCategory)
        parcel.writeInt(propSurface)
        parcel.writeInt(propPrix)
        parcel.writeInt(propEtage)
        parcel.writeInt(propEtageSur)
        parcel.writeInt(propEnery)
        parcel.writeInt(propGes)
        parcel.writeString(infoComp)
        parcel.writeString(prop_meta_chambres)
        parcel.writeString(prop_meta_suites)
        parcel.writeString(prop_meta_salles_de_bains)
        parcel.writeString(prop_meta_salles_d_eau)
        parcel.writeString(prop_meta_bureaux)
        parcel.writeString(prop_meta_dressing)
        parcel.writeString(prop_meta_garages)
        parcel.writeString(prop_meta_caves)
        parcel.writeString(prop_meta_balcons)
        parcel.writeString(prop_meta_terrasse)
        parcel.writeString(prop_meta_surface_terrain)
        parcel.writeString(prop_meta_annee)
        parcel.writeString(prop_meta_piscine)
        parcel.writeString(prop_meta_piscinable)
        parcel.writeString(prop_meta_pool_house)
        parcel.writeString(prop_meta_sans_vis_a_vis)
        parcel.writeString(prop_meta_ascenseur)
        parcel.writeString(prop_meta_duplex)
        parcel.writeString(prop_meta_triplex)
        parcel.writeString(prop_meta_rez_de_jardin)
        parcel.writeString(prop_localisation_ville)
        parcel.writeInt(prop_localisation_codepostal)
        parcel.writeString(prop_localisation_complement_adresse)
        parcel.writeString(proInterphoneName)
        parcel.writeString(propCodeportail)
        parcel.writeString(propInfos)
        parcel.writeString(propMainPhoto)
        parcel.writeString(propAlbumPhoto1)
        parcel.writeString(propAlbumPhoto2)
        parcel.writeString(propAlbumPhoto3)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "PropertyAdd(userId=$userId, propId=$propId, propType=$propType, propCategory=$propCategory, propSurface=$propSurface, propPrix=$propPrix, propEtage=$propEtage, propEtageSur=$propEtageSur, propEnery=$propEnery, propGes=$propGes, infoComp='$infoComp', prop_meta_chambres='$prop_meta_chambres', prop_meta_suites='$prop_meta_suites', prop_meta_salles_de_bains='$prop_meta_salles_de_bains', prop_meta_salles_d_eau='$prop_meta_salles_d_eau', prop_meta_bureaux='$prop_meta_bureaux', prop_meta_dressing='$prop_meta_dressing', prop_meta_garages='$prop_meta_garages', prop_meta_caves='$prop_meta_caves', prop_meta_balcons='$prop_meta_balcons', prop_meta_terrasse='$prop_meta_terrasse', prop_meta_surface_terrain='$prop_meta_surface_terrain', prop_meta_annee='$prop_meta_annee', prop_meta_piscine='$prop_meta_piscine', prop_meta_piscinable='$prop_meta_piscinable', prop_meta_pool_house='$prop_meta_pool_house', prop_meta_sans_vis_a_vis='$prop_meta_sans_vis_a_vis', prop_meta_ascenseur='$prop_meta_ascenseur', prop_meta_duplex='$prop_meta_duplex', prop_meta_triplex='$prop_meta_triplex', prop_meta_rez_de_jardin='$prop_meta_rez_de_jardin', prop_localisation_ville='$prop_localisation_ville', prop_localisation_codepostal=$prop_localisation_codepostal, prop_localisation_complement_adresse='$prop_localisation_complement_adresse', proInterphoneName='$proInterphoneName', propCodeportail='$propCodeportail', propInfos='$propInfos', propMainPhoto='$propMainPhoto', propAlbumPhoto1='$propAlbumPhoto1', propAlbumPhoto2='$propAlbumPhoto2', propAlbumPhoto3='$propAlbumPhoto3')"
    }

    companion object CREATOR : Parcelable.Creator<PropertyAdd> {
        override fun createFromParcel(parcel: Parcel): PropertyAdd {
            return PropertyAdd(parcel)
        }

        override fun newArray(size: Int): Array<PropertyAdd?> {
            return arrayOfNulls(size)
        }
    }
}