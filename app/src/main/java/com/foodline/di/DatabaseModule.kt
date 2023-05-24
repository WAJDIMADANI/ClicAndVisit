package com.foodline.di

import android.content.Context
import com.foodline.data.db.Database
import com.foodline.data.db.DatabaseBuilder
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
