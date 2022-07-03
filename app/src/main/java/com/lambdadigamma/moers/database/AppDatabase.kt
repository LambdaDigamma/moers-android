package com.lambdadigamma.moers.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lambdadigamma.newsfeature.NewsDao
import com.lambdadigamma.newsfeature.RssItem
import com.lambdadigamma.rubbish.RubbishCollectionItem
import com.lambdadigamma.rubbish.RubbishCollectionStreet
import com.lambdadigamma.rubbish.RubbishDao

@Database(
    entities = [
//    Entry::class,
//    Event::class,
//    EventExtras::class,
//    Camera::class,
//    PetrolStation::class,
        RssItem::class,
        RubbishCollectionStreet::class,
        RubbishCollectionItem::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    abstract fun rubbishDao(): RubbishDao

}