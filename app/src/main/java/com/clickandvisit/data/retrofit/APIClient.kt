package com.clickandvisit.data.retrofit

import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.chat.ContactOwnerResponse
import com.clickandvisit.data.model.chat.DiscussionsResponse
import com.clickandvisit.data.model.chat.MessagesResponse
import com.clickandvisit.data.model.property.FavoritesResponse
import com.clickandvisit.data.model.property.PropertyDetailsResponse
import com.clickandvisit.data.model.property.SavedSearchResponse
import com.clickandvisit.data.model.property.SearchResponse
import com.clickandvisit.data.model.property.add.PropertyAddResponse
import com.clickandvisit.data.model.reservation.AvailabilityResponse
import com.clickandvisit.data.model.reservation.ReservationResponse
import com.clickandvisit.data.model.reservation.ReservedPropertyResponse
import com.clickandvisit.data.model.reservation.ResultModel
import com.clickandvisit.data.model.user.*
import com.clickandvisit.data.model.user.signup.SignupResponse
import retrofit2.http.*


interface APIClient {

    /** user **/

    @Multipart
    @POST("user_register")
    suspend fun signUp(
        @Field("professionel_particulier") proPar: Int,
        @Field("siret") siret: String,
        @Field("raison_social") rSocial: String,
        @Field("civilite") civility: Int,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone_number") phoneNumber: String,
        //@Part photo: MultipartBody.Part
    ): SignupResponse

    @FormUrlEncoded
    @POST("user_login")
    suspend fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): SignupResponse


    @GET("get_user")
    suspend fun getUser(
        @Query("user_id") id: Int
    ): UserResponse

    @FormUrlEncoded
    @POST("user_update")
    suspend fun userUpdate(
        @Field("user_id") id: String,
        @Field("email") email: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("professionel_particulier") proPar: String,
        @Field("civilite") civility: String,
        @Field("phone_number") phoneNumber: String,
        @Field("siret") siret: String,
        @Field("raison_social") rSocial: String,
        @Field("profile_photo") photo: String
    ): UserResponse

    @FormUrlEncoded
    @POST("send_activation_code")
    suspend fun sendActivationCode(
        @Field("user_id") userId: Int,
    ): SignupResponse

    @FormUrlEncoded
    @POST("activate_account")
    suspend fun activateAccount(
        @Field("user_id") userId: Int,
        @Field("code_activation") activationCode: String
    ): SignupResponse

    @FormUrlEncoded
    @POST("remove_account")
    suspend fun removeAccount(
        @Field("user_id") userId: Int,
    ): GlobalResponse

    @FormUrlEncoded
    @POST("report_user")
    suspend fun reportUser(
        @Field("user_id") userId: Int,
        @Field("user_to_report") userRId: Int,
        @Field("message") message: String
    ): Void

    @FormUrlEncoded
    @POST("set_push_token")
    suspend fun setPushToken(
        @Field("user_id") userId: Int,
        @Field("token") token: String,
        @Field("device") device: String,
    ): TokenResponse


    /** Property **/

    @FormUrlEncoded
    @POST("search")
    suspend fun search(
        @Field("sortby") sortBy: String? ,  // date/price/surface
        @Field("sorthow") sortHow: String? // asc/desc
    ): SearchResponse


    @FormUrlEncoded
    @POST("search")
    suspend fun search(
        @Field("user_id") userId: Int
    ): SearchResponse

    @FormUrlEncoded
    @POST("search")
    suspend fun search(
        @Field("type_annonce") adsType: Int?,
        @Field("categorie") category: String?,
        @Field("min_rooms") minRooms: String?,
        @Field("max_rooms") maxRooms: String?,
        @Field("min-area") minArea: Int?,
        @Field("max-area") maxArea: Int?,
        @Field("min-price") minPrice: Int?,
        @Field("max-price") maxPrice: Int?,
        @Field("favorite_user_id") favoriteUserId: Int?,
        @Field("save_search") saveSearch: Int?, //0 : No / 1 : Yes
        @Field("adresse") address: String?,
        @Field("sortby") sortBy: String?,  // date/price/surface
        @Field("sorthow") sortHow: String? // asc/desc
    ): SearchResponse


    @GET("property_details")
    suspend fun propertyDetails(
        @Query("user_id") userId: Int,
        @Query("id") propertyId: Int
    ): PropertyDetailsResponse

    @FormUrlEncoded
    @POST("add_favorite")
    suspend fun addRemoveFavorite(
        @Field("property_id") propertyId: Int,
        @Field("user_id") userId: Int,
        @Field("action") action: String
    ): GlobalResponse


    @GET("list_favorites")
    suspend fun     favoriteList(
        @Query("user_id") userId: Int
    ): FavoritesResponse

    @FormUrlEncoded
    @POST("create_update_property")
    suspend fun createUpdateProperty(
        @Field("user_id") userId: Int,
        @Field("_logement_id") propId: Int?, // only for edit
        @Field("prop_type") propType: Int?,
        @Field("prop_category") propCategory: Int?,
        @Field("prop_surface") propSurface: Int?,
        @Field("prop_prix") propPrix: Int?,
        @Field("prop_etage") propEtage: Int?,
        @Field("prop_etage_sur") propEtageSur: Int?,
        @Field("prop_enery") propEnergy: Int?,
        @Field("prop_ges") propGes: Int?,
        @Field("prop_infos_complementaires") infoComp: String?,
        @Field("prop_meta_chambres") prop_meta_chambres: String?,
        @Field("prop_meta_suites") prop_meta_suites: String?,
        @Field("prop_meta_salles_de_bains") prop_meta_salles_de_bains: String?,
        @Field("prop_meta_salles_d_eau") prop_meta_salles_d_eau: String?,
        @Field("prop_meta_bureaux") prop_meta_bureaux: String?,
        @Field("prop_meta_dressing") prop_meta_dressing: String?,
        @Field("prop_meta_garages") prop_meta_garages: String?,
        @Field("prop_meta_caves") prop_meta_caves: String?,
        @Field("prop_meta_balcons") prop_meta_balcons: String?,
        @Field("prop_meta_terrasse") prop_meta_terrasse: String?,
       // @Field("prop_meta_surface_terrain") prop_meta_surface_terrain: String,
        @Field("prop_meta_annee") prop_meta_annee: String?,
        @Field("prop_meta_piscine") prop_meta_piscine: String?,
        @Field("prop_meta_piscinable") prop_meta_piscinable: String?,
        @Field("prop_meta_pool_house") prop_meta_pool_house: String?,
        @Field("prop_meta_sans_vis_a_vis") prop_meta_sans_vis_a_vis: String?,
        @Field("prop_meta_ascenseur") prop_meta_ascenseur: String?,
        @Field("prop_meta_duplex") prop_meta_duplex: String?,
        @Field("prop_meta_triplex") prop_meta_triplex: String?,
        @Field("prop_meta_rez_de_jardin") prop_meta_rez_de_jardin: String?,
        @Field("prop_localisation_ville") prop_localisation_ville: String?,
        @Field("prop_localisation_codepostal") prop_localisation_codepostal: String?,
        @Field("prop_localisation_complement_adresse") prop_localisation_complement_adresse: String?,
        @Field("prop_nom_interphone") proInterphoneName: String?,
        @Field("prop_codeportail") propCodeportail: String?,
        @Field("prop_autres_informations") propInfos: String?,
        @Field("prop_main_photo") propMainPhoto: String?,
        @Field("prop_album_photo[]") propAlbumPhoto1: String?,
        @Field("prop_album_photo[]") propAlbumPhoto2: String?,
        @Field("prop_album_photo[]") propAlbumPhoto3: String?
    ): PropertyAddResponse

    @FormUrlEncoded
    @POST("enable_disable_property")
    suspend fun enableDisableProperty(
        @Field("logement_id") propId: Int,
        @Field("enable_disable") enableDisable: Int, // 0 : Disable / 1 : Enable
    ): GlobalResponse

    @FormUrlEncoded
    @POST("delete_search")
    suspend fun deleteSearch(
        @Field("search_id") searchId: Int
    ): GlobalResponse


    @GET("get_saved_search")
    suspend fun getSavedSearch(
        @Query("user_id") userId: Int
    ): SavedSearchResponse


    /** Reservation **/


    @GET("get_avaibility")
    suspend fun getAvailability(
        @Query("date") date: String,
        @Query("property_id") propId: Int
    ): AvailabilityResponse


    @GET("get_all_reserved")
    suspend fun getAllReserved(
        @Query("property_id") propId: Int
    ): ReservedPropertyResponse

    @FormUrlEncoded
    @POST("reservate")
    suspend fun reserve(
        @Field("user_id") userId: Int,
        @Field("property_id") propertyId: Int,
        @Field("date_time") dateTime: String
    ): ReservationResponse

    @FormUrlEncoded
    @POST("set_avaibility")
    suspend fun setAvailability(
        @Field("user_id") userId: Int,
        @Field("property_id") propertyId: Int,
        @Field("date_time") dateTime: String, // 2023-03-23 07:00
        @Field("remove_add") removeAdd: String  // add/remove
    ): ResultModel

    @FormUrlEncoded
    @POST("accept_refuse_reservation")
    suspend fun acceptRefuseReservation(
        @Field("property_id") propertyId: Int,
        @Field("reservation_id") reservationId: Int,
        @Field("accept") accept: Boolean
    ): ReservationResponse


    @GET("get_reservations")
    suspend fun getReservations(
        @Query("user_id") id: Int,
        @Query("sent") accept: Boolean
    ): ReservationResponse


    /** Chat **/

    @FormUrlEncoded
    @POST("contact_owner")
    suspend fun contactOwner(
        @Field("user_id") userId: Int,
        @Field("property_id") propertyId: Int,
        @Field("message") message: String
    ): Void

    @FormUrlEncoded
    @POST("send_message")
    suspend fun sendMessage(
        @Field("user_id") userId: Int,
        @Field("discution_id") discussionId: Int,
        @Field("message") message: String
    ): GlobalResponse


    @GET("get_discutions")
    suspend fun getDiscussions(
        @Query("user_id") id: Int
    ): DiscussionsResponse


    @GET("get_discutions_messages")
    suspend fun getMessages(
        @Query("user_id") id: Int,
        @Query("discution_id") discussionId: Int
    ): MessagesResponse

}