package com.clickandvisit.data.retrofit

import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.chat.DiscussionsResponse
import com.clickandvisit.data.model.chat.ContactOwnerResponse
import com.clickandvisit.data.model.chat.MessagesResponse
import com.clickandvisit.data.model.property.FavoriteRequest
import com.clickandvisit.data.model.property.PropertyDetailsResponse
import com.clickandvisit.data.model.property.SearchRequest
import com.clickandvisit.data.model.property.SearchResponse
import com.clickandvisit.data.model.property.add.PropertyAdd
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

    @FormUrlEncoded
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
        @Field("phone_number") phoneNumber: String
    ): SignupResponse

    @FormUrlEncoded
    @POST("user_login")
    suspend fun signIn(
        @Body login: Login
    ): SignupResponse

    @FormUrlEncoded
    @GET("get_user?user_id={id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): UserResponse

    @FormUrlEncoded
    @POST("user_update")
    suspend fun userUpdate(
        @Field("id") id: String,
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
        @Body userId: Int
    ): SignupResponse

    @FormUrlEncoded
    @POST("activate_account")
    suspend fun activateAccount(
        @Body req: ActivateAccountRequest
    ): SignupResponse

    @FormUrlEncoded
    @POST("remove_account")
    suspend fun removeAccount(
        @Body userId: Int
    ): Void //TODO:

    @FormUrlEncoded
    @POST("report_user")
    suspend fun reportUser(
        @Field("user_id") userId: Int,
        @Field("user_to_report") userRId: Int,
        @Field("message") message: String
    ): ReportUserResponse

    @FormUrlEncoded
    @POST("set_push_token")
    suspend fun setPushToken(
        @Field("user_id") userId: Int,
        @Field("token") token: Int,
        @Field("device") device: String,
    ): TokenResponse


    /** property **/

    @FormUrlEncoded
    @POST("search")
    suspend fun search(
        @Body userId: SearchRequest
    ): SearchResponse

    @FormUrlEncoded
    @GET("property_details?id={propertyId}&user_id={userId}")
    suspend fun propertyDetails(
        @Path("propertyId") propertyId: Int,
        @Path("userId") userId: Int
    ): PropertyDetailsResponse

    @FormUrlEncoded
    @POST("add_favorite")
    suspend fun addRemoveFavorite(
        @Body favoriteRequest: FavoriteRequest
    ): Void //TODO: FavoriteResponse

    @FormUrlEncoded
    @GET("list_favorites?user_id={userId}")
    suspend fun favoriteList(
        @Path("userId") userId: Int
    ): Void //TODO: favoriteList

    @FormUrlEncoded
    @POST("create_update_property")
    suspend fun createUpdateProperty(
        @Body propertyAdd: PropertyAdd
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

    @FormUrlEncoded
    @GET("get_saved_search?user_id={userId}")
    suspend fun getSavedSearch(
        @Path("userId") userId: Int
    ): GlobalResponse


    /** Reservation **/

    @FormUrlEncoded
    @GET("get_avaibility?date={date}&property_id={propId}")
    suspend fun getAvailability(
        @Path("date") date: String,
        @Path("propId") propId: Int
    ): AvailabilityResponse

    @FormUrlEncoded
    @GET("get_all_reserved?property_id={propId}")
    suspend fun getAllReserved(
        @Path("propId") propId: Int
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

    @FormUrlEncoded
    @GET("get_reservations?user_id={user_id}&sent={sent}")
    suspend fun getReservations(
        @Path("user_id") userId: Int,
        @Path("sent") accept: Boolean
    ): ReservationResponse


    /** Chat **/

    @FormUrlEncoded
    @POST("contact_owner")
    suspend fun contactOwner(
        @Path("user_id") userId: Int,
        @Field("property_id") propertyId: Int,
        @Path("message") message: String
    ): ContactOwnerResponse

    @FormUrlEncoded
    @POST("send_message")
    suspend fun sendMessage(
        @Path("user_id") userId: Int,
        @Field("discution_id") discussionId: Int,
        @Path("message") message: String
    ): GlobalResponse

    @FormUrlEncoded
    @GET("get_discutions?user_id={user_id}")
    suspend fun getDiscussions(
        @Path("user_id") userId: Int
    ): DiscussionsResponse

    @FormUrlEncoded
    @GET("get_discutions_messages?user_id={user_id}&discution_id={discution_id}")
    suspend fun getMessages(
        @Path("user_id") userId: Int,
        @Field("discution_id") discussionId: Int
    ): MessagesResponse

}