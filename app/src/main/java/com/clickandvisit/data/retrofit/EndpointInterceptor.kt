package com.clickandvisit.data.retrofit

import android.content.Context
import com.clickandvisit.global.helper.SharedPreferences
import com.clickandvisit.global.utils.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class EndpointInterceptor(private val preferences: SharedPreferences,private val context: Context) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (context.isNetworkAvailable()) {
            if (preferences.isConnected()) {
                request = request.newBuilder()
                    .method(request.method, request.body)
                   // .addHeader("Authorization", "Bearer " + preferences.getToken())
                    .build()
            }
        } else {
            throw NetworkNotFoundException()
        }
        val response = chain.proceed(request)
        return response
    }

    class NetworkNotFoundException : IOException("No network available")

}
