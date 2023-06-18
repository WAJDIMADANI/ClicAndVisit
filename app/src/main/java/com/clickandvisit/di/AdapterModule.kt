package com.clickandvisit.di

import com.clickandvisit.ui.home.SearchAdapter
import com.clickandvisit.ui.user.chat.ChatAdapter
import com.clickandvisit.ui.user.chat.conv.ConvAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(ActivityComponent::class)
class ActivityAdapterModule {

    @Provides
    fun provideChatAdapter(picasso: Picasso): ChatAdapter {
        return ChatAdapter(picasso)
    }

    @Provides
    fun provideConvAdapter(picasso: Picasso): ConvAdapter {
        return ConvAdapter(picasso)
    }

    @Provides
    fun provideSearchAdapter(picasso: Picasso): SearchAdapter {
        return SearchAdapter(picasso)
    }

}

@Module
@InstallIn(FragmentComponent::class)
class FragmentAdapterModule {


}