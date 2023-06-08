package com.clickandvisit.data.retrofit

import com.clickandvisit.data.model.property.FavoriteRequest
import com.clickandvisit.data.model.property.PropertyDetailsResponse
import com.clickandvisit.data.model.property.SearchRequest
import com.clickandvisit.data.model.property.SearchResponse
import com.clickandvisit.data.model.user.*
import com.clickandvisit.data.model.user.signup.SignupRequest
import com.clickandvisit.data.model.user.signup.SignupResponse
import retrofit2.http.*


interface APIClient {

    @FormUrlEncoded
    @POST("medamine/paginate/signin.json")
    suspend fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Void

    @FormUrlEncoded
    @POST("medamine/paginate/signinf.json")
    suspend fun signUpAndCache(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String
    ): Void

/*
    @GET("users/{id}")
    suspend fun getMyProfile(@Path("id") id: Int): User

    @POST("login")
    suspend fun signIn(
        @Body login: Login
    ): LoginResponse
*/

    // user
    @POST("user_register")
    suspend fun signUp(
        @Body signupRequest: SignupRequest
    ): SignupResponse

    @POST("user_login")
    suspend fun signIn(
        @Body login: Login
    ): SignupResponse

    @GET("get_user?user_id={id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): User

    @POST("user_update")
    suspend fun userUpdate(
        @Body user: User
    ): Void //TODO:

    @POST("send_activation_code")
    suspend fun sendActivationCode(
        @Body userId: Int
    ): Void //TODO:

    @POST("activate_account")
    suspend fun activateAccount(
        @Body req: ActivateAccountRequest
    ): Void //TODO:

    @POST("remove_account")
    suspend fun removeAccount(
        @Body userId: Int
    ): Void //TODO:

    @POST("report_user")
    suspend fun reportUser(
        @Body userId: ReportUserRequest
    ): Void //TODO:

    @POST("set_push_token")
    suspend fun setPushToken(
        @Body userId: PushTokenRequest
    ): Void //TODO:


    // property

    @POST("search")
    suspend fun search(
        @Body userId: SearchRequest
    ): SearchResponse

    @GET("property_details?id={propertyId}&user_id={userId}")
    suspend fun propertyDetails(
        @Path("propertyId") propertyId: Int,
        @Path("userId") userId: Int
    ): PropertyDetailsResponse

    @POST("add_favorite")
    suspend fun addRemoveFavorite(
        @Body favoriteRequest: FavoriteRequest
    ): Void //TODO: FavoriteResponse

    @GET("list_favorites?user_id={userId}")
    suspend fun favoriteList(
        @Path("userId") userId: Int
    ): Void //TODO: favoriteList


}

