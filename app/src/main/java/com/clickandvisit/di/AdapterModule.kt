package com.clickandvisit.di

import com.clickandvisit.ui.ads.adsdetails.RoomAdapter
import com.clickandvisit.ui.ads.adslist.PropertyAdapter
import com.clickandvisit.ui.ads.favourites.FavouritesAdapter
import com.clickandvisit.ui.ads.search.MySearchAdapter
import com.clickandvisit.ui.home.SearchAdapter
import com.clickandvisit.ui.user.chat.ChatAdapter
import com.clickandvisit.ui.user.chat.conv.ConvAdapter
import com.clickandvisit.ui.user.meet.MeetAdapter
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
    fun provideSearchAdapter(picasso: Picasso): MySearchAdapter {
        return MySearchAdapter(picasso)
    }

    @Provides
    fun providePropertyAdapter(picasso: Picasso): PropertyAdapter {
        return PropertyAdapter(picasso)
    }

    @Provides
    fun provideMeetAdapter(picasso: Picasso): MeetAdapter {
        return MeetAdapter(picasso)
    }

    @Provides
    fun provideMySearchAdapter(picasso: Picasso): SearchAdapter {
        return SearchAdapter(picasso)
    }

    @Provides
    fun provideFavouritesAdapter(picasso: Picasso): FavouritesAdapter {
        return FavouritesAdapter(picasso)
    }

    @Provides
    fun provideRoomAdapter(): RoomAdapter {
        return RoomAdapter()
    }

}

@Module
@InstallIn(FragmentComponent::class)
class FragmentAdapterModule {


}