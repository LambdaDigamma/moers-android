package com.lambdadigamma.rubbish

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.lambdadigamma.core.AppExecutors
import com.lambdadigamma.core.NetworkBoundResource
import com.lambdadigamma.core.Resource
import com.lambdadigamma.rubbish.settings.RubbishSettings
import com.lambdadigamma.rubbish.source.RubbishApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject

class RubbishRepository @Inject constructor(
    @ApplicationContext private val context: Context,
//    private val locationService: LocationService,
//    private val remoteDataSource: RubbishRemoteDataSource,
    val remoteDataSource: RubbishApi,
    private val rubbishDao: RubbishDao,
    private val appExecutors: AppExecutors // = AppExecutors()
) {

    private val dataStore: DataStore<RubbishSettings> = context.rubbishSettingsDataStore

    private val latestStreetsMutex = Mutex()
    private var latestStreets: List<RubbishCollectionStreet> = emptyList()

    private val latestPickupItemsMutex = Mutex()
    private var latestPickupItems: List<RubbishCollectionItem> = emptyList()

//    suspend fun loadStreets(
//        streetName: String? = null,
//        refresh: Boolean = true
//    ): List<RubbishCollectionStreet> {
//
//        if (refresh || latestStreets.isEmpty()) {
//            val networkResult = remoteDataSource.fetchStreets(streetName = streetName)
//            // Thread-safe write to latestStreets
//            latestStreetsMutex.withLock {
//                this.latestStreets = networkResult
//            }
//        }
//
//        return latestStreetsMutex.withLock { this.latestStreets }
//    }

    fun getRubbishStreets(streetName: String? = null): LiveData<Resource<List<RubbishCollectionStreet>?>> {
        return object :
            NetworkBoundResource<List<RubbishCollectionStreet>, List<RubbishCollectionStreet>>(
                appExecutors
            ) {

            override fun saveCallResult(item: List<RubbishCollectionStreet>) {
//                LastUpdate.set(Date(), "rubbishStreets")
                System.out.println(item)
                rubbishDao.insertRubbishStreets(item)
            }

            override fun shouldFetch(data: List<RubbishCollectionStreet>?): Boolean {
//                LastUpdate.get("rubbishStreets")?.minuteInterval() ?: 120 > 60
                return true
//                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = rubbishDao.getRubbishStreets()

            override fun createCall(): LiveData<Resource<List<RubbishCollectionStreet>>> {

                Log.d("API", "Fetching streets")

                val fetchedStreets = remoteDataSource.fetchStreets(streetName = streetName)

                return Transformations.map(fetchedStreets) { data ->
                    return@map data.transform { it.data }
                }
            }

        }.asLiveData()
    }

    fun loadRubbishCollectionItems(): LiveData<Resource<List<RubbishCollectionItem>?>> {

        return object :
            NetworkBoundResource<List<RubbishCollectionItem>, List<RubbishCollectionItem>>(
                appExecutors
            ) {

            override fun saveCallResult(item: List<RubbishCollectionItem>) {
                Log.d("Api", "Saving ${item.count()} items!")
                rubbishDao.insertRubbishCollectionItems(item)
            }

            override fun shouldFetch(data: List<RubbishCollectionItem>?): Boolean =
                true // data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<List<RubbishCollectionItem>> {
                Log.d("Api", "Loading items from db")
                return rubbishDao.loadAllRubbishCollectionItems()
            }

            override fun createCall(): LiveData<Resource<List<RubbishCollectionItem>>> {


                return Transformations.switchMap(dataStore.data.asLiveData()) {
                    Log.d("Api", it.rubbishCollectionStreet.id.toString())
                    Transformations.map(remoteDataSource.getPickupItems(it.rubbishCollectionStreet.id)) { resource ->
                        Resource.success(resource.data?.data.orEmpty())
                    }
                }

//                val t = dataStore.data.asLiveData().map {
//                    remoteDataSource.getPickupItems(it.rubbishCollectionStreet.id)
//                }
//
//                t.asLiveData()

//                val d = MutableLiveData<Resource<List<RubbishCollectionItem>>>()
//                remoteDataSource.fetchCollectionItems(streetId)
            }
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

    val currentStreet: Flow<RubbishSettings.RubbishCollectionStreet> = dataStore.data
        .map { settings ->
            settings.rubbishCollectionStreet
        }

    suspend fun changeReminderTime(hours: Int, minutes: Int) {
//        context.rubbishSettingsDataStore
    }

}