package com.lambdadigamma.rubbish

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambdadigamma.core.AppExecutors
import com.lambdadigamma.core.NetworkBoundResource
import com.lambdadigamma.core.Resource
import com.lambdadigamma.rubbish.settings.RubbishSettings
import com.lambdadigamma.rubbish.source.RubbishRemoteDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class RubbishRepository @Inject constructor(
    @ApplicationContext private val context: Context,
//    private val locationService: LocationService,
    private val remoteDataSource: RubbishRemoteDataSource,
    private val rubbishDao: RubbishDao,
    private val appExecutors: AppExecutors = AppExecutors()
) {

    private val dataStore: DataStore<RubbishSettings> = context.rubbishSettingsDataStore

    private val latestStreetsMutex = Mutex()
    private var latestStreets: List<RubbishCollectionStreet> = emptyList()

    private val latestPickupItemsMutex = Mutex()
    private var latestPickupItems: List<RubbishCollectionItem> = emptyList()

    suspend fun loadStreets(
        streetName: String? = null,
        refresh: Boolean = true
    ): List<RubbishCollectionStreet> {

        if (refresh || latestStreets.isEmpty()) {
            val networkResult = remoteDataSource.fetchStreets(streetName = streetName)
            // Thread-safe write to latestStreets
            latestStreetsMutex.withLock {
                this.latestStreets = networkResult
            }
        }

        return latestStreetsMutex.withLock { this.latestStreets }
    }

    fun loadRubbishCollectionItems(): LiveData<Resource<List<RubbishCollectionItem>?>> {

        return object :
            NetworkBoundResource<List<RubbishCollectionItem>, List<RubbishCollectionItem>>(
                appExecutors
            ) {

            override fun saveCallResult(item: List<RubbishCollectionItem>) {
                rubbishDao.insertRubbishCollectionItems(item)
            }

            override fun shouldFetch(data: List<RubbishCollectionItem>?): Boolean =
                true // data == null || data.isEmpty()

            override fun loadFromDb() = rubbishDao.loadAllRubbishCollectionItems()

            override fun createCall(): LiveData<Resource<List<RubbishCollectionItem>>> {

                return MutableLiveData()


//                val d = MutableLiveData<Resource<List<RubbishCollectionItem>>>()
//                remoteDataSource.fetchCollectionItems(streetId)
            }
            //                remoteDataSource.fetchCollectionItems()


        }.asLiveData()

//        if (latestPickupItems.isEmpty()) {
//            dataStore.data.collectLatest {
//                val networkResult = remoteDataSource
//                    .fetchCollectionItems(it.rubbishCollectionStreet.id)
//                return@collectLatest latestPickupItemsMutex.withLock {
//                    this.latestPickupItems = networkResult
//                }
//            }
//        }
//
//        return latestPickupItemsMutex.withLock { this.latestPickupItems }
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

    suspend fun setStreet(id: Int, name: String) {
        val rubbishSettingsStreet = RubbishSettings.RubbishCollectionStreet
            .newBuilder()
            .setId(id.toLong())
            .setName(name)
            .build()

        dataStore.updateData { settings ->
            settings.toBuilder()
                .setRubbishCollectionStreet(rubbishSettingsStreet)
                .build()
        }
    }

    suspend fun changeReminderTime(hours: Int, minutes: Int) {
//        context.rubbishSettingsDataStore
    }

}