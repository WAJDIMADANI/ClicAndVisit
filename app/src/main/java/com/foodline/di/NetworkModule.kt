package com.foodline.di

import android.content.Context
import com.foodline.BuildConfig
import com.foodline.data.retrofit.EndpointInterceptor
import com.foodline.global.helper.SharedPreferences
import com.foodline.global.utils.DebugLog
import com.foodline.global.utils.TAG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

private const val CONNECT_TIMEOUT = 15
private const val WRITE_TIMEOUT = 15
private const val READ_TIMEOUT = 15

@Qualifier
annotation class PicassoScope

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor( object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                DebugLog.d(TAG,message)
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun endpointInterceptor(preferences: SharedPreferences,context: Context): EndpointInterceptor {
        return EndpointInterceptor(preferences, context)
    }

    @Provides
    @Singleton
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        endpointInterceptor: EndpointInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(endpointInterceptor)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun cacheFile(context: Context): File {
        return File(context.cacheDir, "okhttp_cache")
    }

    @Provides
    @Singleton
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, (10 * 1000 * 1000).toLong()) //10MB Cache
    }

    @Provides
    @PicassoScope
    @Singleton
    fun okHttpPicassoClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .cache(cache)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }
}
