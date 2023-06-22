package com.clickandvisit.data.model.property.add

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PropertyAdd(

    @Json(name = "_logement_id")
    var propId: Int? = null, // only for edit

    @Json(name = "prop_type")
    var propType: Int? = null,
    @Json(name = "prop_category")
    var propCategory: Int? = null,
    @Json(name = "prop_surface")
    var propSurface: Int? = null,
    @Json(name = "prop_prix")
    var propPrix: Int? = null,
    @Json(name = "prop_etage")
    var propEtage: Int? = null,
    @Json(name = "prop_etage_sur")
    var propEtageSur: Int? = null,
    @Json(name = "prop_enery")
    var propEnery: Int? = null,
    @Json(name = "prop_ges")
    var propGes: Int? = null,
    @Json(name = "prop_infos_complementaires")
    var infoComp: String? = null,


    @Json(name = "prop_meta_chambres")
    var prop_meta_chambres: String? = null,
    @Json(name = "prop_meta_suites")
    var prop_meta_suites: String? = null,
    @Json(name = "prop_meta_salles_de_bains")
    var prop_meta_salles_de_bains: String? = null,
    @Json(name = "prop_meta_salles_d_eau")
    var prop_meta_salles_d_eau: String? = null,
    @Json(name = "prop_meta_bureaux")
    var prop_meta_bureaux: String? = null,
    @Json(name = "prop_meta_dressing")
    var prop_meta_dressing: String? = null,
    @Json(name = "prop_meta_garages")
    var prop_meta_garages: String? = null,
    @Json(name = "prop_meta_caves")
    var prop_meta_caves: String? = null,
    @Json(name = "prop_meta_balcons")
    var prop_meta_balcons: String? = null,
    @Json(name = "prop_meta_terrasse")
    var prop_meta_terrasse: String? = null,
/*    @Json(name = "prop_meta_surface_terrain")
    var prop_meta_surface_terrain: String? = null,*/
    @Json(name = "prop_meta_annee")
    var prop_meta_annee: String? = null,
    @Json(name = "prop_meta_piscine")
    var prop_meta_piscine: String? = null,
    @Json(name = "prop_meta_piscinable")
    var prop_meta_piscinable: String? = null,
    @Json(name = "prop_meta_pool_house")
    var prop_meta_pool_house: String? = null,
    @Json(name = "prop_meta_sans_vis_a_vis")
    var prop_meta_sans_vis_a_vis: String? = null,
    @Json(name = "prop_meta_ascenseur")
    var prop_meta_ascenseur: String? = null,
    @Json(name = "prop_meta_duplex")
    var prop_meta_duplex: String? = null,
    @Json(name = "prop_meta_triplex")
    var prop_meta_triplex: String? = null,
    @Json(name = "prop_meta_rez_de_jardin")
    var prop_meta_rez_de_jardin: String? = null,

    @Json(name = "prop_localisation_ville")
    var prop_localisation_ville: String? = null,
    @Json(name = "prop_localisation_codepostal")
    var prop_localisation_codepostal: String? = null,
    @Json(name = "prop_localisation_complement_adresse")
    var prop_localisation_complement_adresse: String? = null,
    @Json(name = "prop_nom_interphone")
    var proInterphoneName: String? = null,
    @Json(name = "prop_codeportail")
    var propCodeportail: String? = null,
    @Json(name = "prop_autres_informations")
    var propInfos: String? = null,

    // photo
    @Json(name = "prop_main_photo")
    var propMainPhoto: String? = null,
    @Json(name = "prop_album_photo[]")
    var propAlbumPhoto1: String? = null,
    @Json(name = "prop_album_photo[]")
    var propAlbumPhoto2: String? = null,
    @Json(name = "prop_album_photo[]")
    var propAlbumPhoto3: String? = null

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
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(propId)
        parcel.writeValue(propType)
        parcel.writeValue(propCategory)
        parcel.writeValue(propSurface)
        parcel.writeValue(propPrix)
        parcel.writeValue(propEtage)
        parcel.writeValue(propEtageSur)
        parcel.writeValue(propEnery)
        parcel.writeValue(propGes)
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
        parcel.writeString(prop_localisation_codepostal)
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
        return "PropertyAdd(propId=$propId, propType=$propType, propCategory=$propCategory, propSurface=$propSurface, propPrix=$propPrix, propEtage=$propEtage, propEtageSur=$propEtageSur, propEnery=$propEnery, propGes=$propGes, infoComp=$infoComp, prop_meta_chambres=$prop_meta_chambres, prop_meta_suites=$prop_meta_suites, prop_meta_salles_de_bains=$prop_meta_salles_de_bains, prop_meta_salles_d_eau=$prop_meta_salles_d_eau, prop_meta_bureaux=$prop_meta_bureaux, prop_meta_dressing=$prop_meta_dressing, prop_meta_garages=$prop_meta_garages, prop_meta_caves=$prop_meta_caves, prop_meta_balcons=$prop_meta_balcons, prop_meta_terrasse=$prop_meta_terrasse, prop_meta_annee=$prop_meta_annee, prop_meta_piscine=$prop_meta_piscine, prop_meta_piscinable=$prop_meta_piscinable, prop_meta_pool_house=$prop_meta_pool_house, prop_meta_sans_vis_a_vis=$prop_meta_sans_vis_a_vis, prop_meta_ascenseur=$prop_meta_ascenseur, prop_meta_duplex=$prop_meta_duplex, prop_meta_triplex=$prop_meta_triplex, prop_meta_rez_de_jardin=$prop_meta_rez_de_jardin, prop_localisation_ville=$prop_localisation_ville, prop_localisation_codepostal=$prop_localisation_codepostal, prop_localisation_complement_adresse=$prop_localisation_complement_adresse, proInterphoneName=$proInterphoneName, propCodeportail=$propCodeportail, propInfos=$propInfos, propMainPhoto=$propMainPhoto, propAlbumPhoto1=$propAlbumPhoto1, propAlbumPhoto2=$propAlbumPhoto2, propAlbumPhoto3=$propAlbumPhoto3)"
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