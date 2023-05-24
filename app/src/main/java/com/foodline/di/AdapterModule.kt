package com.foodline.di

import com.foodline.ui.home.news.NewsListAdapter
import com.foodline.ui.shared.adapter.CardAdapter
import com.foodline.ui.shared.adapter.NewsAdapter
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
    fun provideNewsPaginateAdapter(picasso: Picasso): NewsListAdapter {
        return NewsListAdapter(picasso)
    }

    @Provides
    fun provideCardAdapter(): CardAdapter {
        return CardAdapter()
    }

}

@Module
@InstallIn(FragmentComponent::class)
class FragmentAdapterModule {

    @Provides
    fun provideNewsCachedAdapter(picasso: Picasso): NewsAdapter {
        return NewsAdapter(picasso)
    }

}