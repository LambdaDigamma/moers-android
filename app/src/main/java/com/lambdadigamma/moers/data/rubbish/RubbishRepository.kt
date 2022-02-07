package com.lambdadigamma.moers.data.rubbish

import android.content.Context
import androidx.datastore.core.DataStore
import com.lambdadigamma.moers.data.rubbish.source.RubbishRemoteDataSource
import com.lambdadigamma.moers.data.settings.RubbishSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RubbishRepository(
    private val context: Context,
//    private val locationService: LocationService,
    private val remoteDataSource: RubbishRemoteDataSource
//    private val rubbishDao: RubbishDao,
) {

    private val dataStore: DataStore<RubbishSettings> = context.rubbishSettingsDataStore

    private val latestStreetsMutex = Mutex()
    private var latestStreets: List<RubbishCollectionStreet> = emptyList()

    suspend fun loadStreets(refresh: Boolean = true): List<RubbishCollectionStreet> {

        if (refresh || latestStreets.isEmpty()) {
            val networkResult = remoteDataSource.fetchStreets()
            // Thread-safe write to latestStreets
            latestStreetsMutex.withLock {
                this.latestStreets = networkResult
            }
        }

        return latestStreetsMutex.withLock { this.latestStreets }
    }

    fun calculateRecommendedStreet() {

    }

    // --- Reminder

    val reminderEnabled: Flow<Boolean> = dataStore.data
        .map { settings ->
            settings.remindersEnabled
        }

    val reminderTime: Flow<RubbishSettings.RubbishReminderTime?> = dataStore.data
        .map { settings ->
            if (settings.hasReminderTime()) {
                return@map settings.reminderTime
            } else {
                return@map null
            }

        }

    suspend fun enableReminders(hours: Int = 20, minutes: Int = 0) {
        context.rubbishSettingsDataStore.updateData { settings ->
            settings.toBuilder()
                .setRemindersEnabled(true)
                .setReminderTime(
                    RubbishSettings.RubbishReminderTime
                        .newBuilder()
                        .setHours(hours)
                        .setMinutes(minutes)
                )
                .build()
        }
    }

    suspend fun disableReminders() {
        context.rubbishSettingsDataStore.updateData { settings ->
            settings.toBuilder()
                .setRemindersEnabled(false)
                .build()
        }
    }

    suspend fun changeReminderTime(hours: Int, minutes: Int) {
//        context.rubbishSettingsDataStore
    }

}