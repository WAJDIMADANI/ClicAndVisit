package com.clickandvisit.data.model.property

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Details(
    @Json(name = "chambres")
    val chambres: String,
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
    val rezDeJardin: String,


)