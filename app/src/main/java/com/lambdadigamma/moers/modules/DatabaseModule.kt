package com.lambdadigamma.moers.modules

import android.content.Context
import com.lambdadigamma.core.AppExecutors
import com.lambdadigamma.core.geo.DefaultGeocodingService
import com.lambdadigamma.core.geo.GeocodingService
import com.lambdadigamma.events.models.EventDao
import com.lambdadigamma.events.models.MeinMoersService
import com.lambdadigamma.fuel.data.FuelService
import com.lambdadigamma.moers.database.AppDatabase
import com.lambdadigamma.moers.database.DatabaseCreator
import com.lambdadigamma.newsfeature.data.NewsDao
import com.lambdadigamma.rubbish.RubbishDao
import com.lambdadigamma.rubbish.source.DefaultRubbishApiService
import com.lambdadigamma.rubbish.source.RubbishApi
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

    @Provides
    fun provideRubbishDao(appDatabase: AppDatabase): RubbishDao {
        return appDatabase.rubbishDao()
    }

    @Provides
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao()
    }

    @Provides
    fun provideRubbishApi(): RubbishApi {
        return DefaultRubbishApiService.getRubbishService()
    }

    @Provides
    fun provideMeinMoersService(): MeinMoersService {
        return MeinMoersService.getMeinMoersService()
    }

    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }

    @Provides
    fun provideGeocodingService(@ApplicationContext appContext: Context): GeocodingService {
        return DefaultGeocodingService(appContext)
    }

    @Provides
    fun provideFuelService(): FuelService {
        return FuelService.getPetrolService()
    }

//    @Provides
//    fun provideRubbishRepository(): RubbishRepository {
//        return RubbishRepository(
//            context = com.lambdadigamma.moers.Application.instance,
//            remoteDataSource = DefaultRubbishApiService.getRubbishService(),
//            appExecutors = AppExecutors(),
//            rubbishDao = provideRubbishDao(provideAppDatabase(com.lambdadigamma.moers.Application.instance))
//        )
//    }

}