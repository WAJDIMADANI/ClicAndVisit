package com.foodline.di

import com.foodline.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object VersionModule {

    @Provides
    fun provideSchedulerProvider(): String {
        return BuildConfig.VERSION_NAME
    }
}
