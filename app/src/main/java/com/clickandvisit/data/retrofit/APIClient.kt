package com.clickandvisit.data.retrofit

import com.clickandvisit.data.model.news.News
import com.clickandvisit.data.model.user.ProfileResponse
import retrofit2.http.*


interface APIClient {

    @FormUrlEncoded
    @POST("medamine/paginate/signin.json")
    suspend fun signIn(@Field("email") email: String, @Field("password") password: String): ProfileResponse

    @FormUrlEncoded
    @POST("medamine/paginate/signinf.json")
    suspend fun signUpAndCache(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String
    ): ProfileResponse


    @GET("medamine/paginate/paginate.php")
    suspend fun getNews(@Query("p") page: Int, @Query("pageSize") pageSize: Int): List<News>

}

