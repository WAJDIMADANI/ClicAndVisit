package com.clickandvisit.di

import android.content.Context
import com.clickandvisit.data.db.Database
import com.clickandvisit.data.db.DatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun databaseProvider(context: Context): Database {
        return DatabaseBuilder.getBingoDatabase(context)
    }
}
