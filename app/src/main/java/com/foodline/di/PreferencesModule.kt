package com.foodline.di

import android.content.Context
import com.foodline.global.helper.SharedPreferences
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun sharedPreferences(context: Context, moshi: Moshi): SharedPreferences {
        return SharedPreferences(context, moshi)
    }
}
