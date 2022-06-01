package com.lambdadigamma.moers.modules

import android.content.Context
import com.lambdadigamma.moers.database.AppDatabase
import com.lambdadigamma.moers.database.DatabaseCreator
import com.lambdadigamma.newsfeature.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return DatabaseCreator.database(appContext)
    }

    @Provides
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao {
        return appDatabase.newsDao()
    }
}