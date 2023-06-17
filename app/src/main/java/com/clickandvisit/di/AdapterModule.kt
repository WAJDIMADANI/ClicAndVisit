package com.clickandvisit.di

import com.clickandvisit.ui.user.chat.ChatAdapter
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
    fun provideChatAdapterAdapter(picasso: Picasso): ChatAdapter {
        return ChatAdapter(picasso)
    }

}

@Module
@InstallIn(FragmentComponent::class)
class FragmentAdapterModule {


}