package com.lambdadigamma.moers.data.rubbish

import android.content.Context
import androidx.datastore.core.DataStore
import com.lambdadigamma.moers.data.settings.RubbishSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RubbishRepository(
    private val context: Context,
//    private val rubbishDao: RubbishDao,
) {

    private val dataStore: DataStore<RubbishSettings> = context.rubbishSettingsDataStore

    val reminderEnabled: Flow<Boolean> = dataStore.data
        .map { settings ->
            settings.remindersEnabled
        }

//    val reminderTime: Flow<(Int, Int)> = dataStore

    suspend fun enableReminders() {
        context.rubbishSettingsDataStore.updateData { settings ->
            settings.toBuilder()
                .setRemindersEnabled(true)
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