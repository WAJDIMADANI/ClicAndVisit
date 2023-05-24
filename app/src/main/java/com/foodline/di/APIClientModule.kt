package com.foodline.di

import com.foodline.BuildConfig
import com.foodline.data.retrofit.APIClient
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIClientModule {


    @Provides
    @Singleton
    fun apiClient(retrofit: Retrofit): APIClient {
        return retrofit.create(APIClient::class.java)
    }


    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }
}
