package com.clickandvisit.di

import com.clickandvisit.data.repository.abs.NewsRepository
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.data.repository.imp.NewsRepositoryImp
import com.clickandvisit.data.repository.imp.UserRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideProfileRepository(profileImp: UserRepositoryImp): UserRepository

    @Binds
    abstract fun provideNewsRepository(newsImp: NewsRepositoryImp): NewsRepository
}
