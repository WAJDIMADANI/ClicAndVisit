package com.clickandvisit.data.model.property.add

import com.squareup.moshi.JsonClass
import okhttp3.MultipartBody


@JsonClass(generateAdapter = true)
data class PropertyAdd(

    var propId: Int? = null, // only for edit

    var propType: Int? = null,
    var propCategory: Int? = null,
    var propSurface: Int? = null,
    var propPrix: String? = null,
    var propEtage: Int? = null,
    var propEtageSur: Int? = null,
    var propEnery: String? = null,
    var propGes: String? = null,
    var infoComp: String? = null,

    var propPieces: String? = null,

    var prop_meta_chambres: String? = null,


    var prop_meta_suites: String? = null,
    var prop_meta_salles_de_bains: String? = null,
    var prop_meta_salles_d_eau: String? = null,
    var prop_meta_bureaux: String? = null,
    var prop_meta_dressing: String? = null,
    var prop_meta_garages: String? = null,
    var prop_meta_caves: String? = null,
    var prop_meta_balcons: String? = null,
    var prop_meta_terrasse: String? = null,
/*    @Json(name = "prop_meta_surface_terrain")
    var prop_meta_surface_terrain: String? = null,*/
    var prop_meta_annee: String? = null,
    var prop_meta_piscine: String? = null,
    var prop_meta_piscinable: String? = null,
    var prop_meta_pool_house: String? = null,
    var prop_meta_sans_vis_a_vis: String? = null,
    var prop_meta_ascenseur: String? = null,
    var prop_meta_duplex: String? = null,
    var prop_meta_triplex: String? = null,
    var prop_meta_rez_de_jardin: String? = null,

    var prop_localisation_ville: String? = null,
    var prop_localisation_codepostal: String? = null,
    var prop_localisation_complement_adresse: String? = null,
    var proInterphoneName: String? = null,
    var propCodeportail: String? = null,
    var propInfos: String? = null,

    // photos
    var propMainPhoto: MultipartBody.Part? = null,

    var propAlbumPhoto1: MultipartBody.Part? = null,
    var propAlbumPhoto2: MultipartBody.Part? = null,
    var propAlbumPhoto3: MultipartBody.Part? = null,
    var propAlbumPhoto4: MultipartBody.Part? = null,
    var propAlbumPhoto5: MultipartBody.Part? = null,
    var propAlbumPhoto6: MultipartBody.Part? = null

)