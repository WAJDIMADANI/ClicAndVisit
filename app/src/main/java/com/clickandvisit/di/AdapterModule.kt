package com.clickandvisit.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(ActivityComponent::class)
class ActivityAdapterModule {


}

@Module
@InstallIn(FragmentComponent::class)
class FragmentAdapterModule {


}