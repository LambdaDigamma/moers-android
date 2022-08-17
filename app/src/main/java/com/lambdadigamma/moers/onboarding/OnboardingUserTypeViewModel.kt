package com.lambdadigamma.moers.onboarding

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lambdadigamma.core.user.UserType
import com.lambdadigamma.core.utils.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

const val USER_TYPE_KEY = "user.user_type"

@HiltViewModel
class OnboardingUserTypeViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val dataStore = context.dataStore

    val userType: Flow<UserType> = dataStore.data
        .map { settings ->
            settings[userTypeKey]?.let {
                try {
                    UserType.fromString(it)
                } catch (e: Exception) {
                    defaultUserType
                }
            } ?: defaultUserType
        }

    fun updateUserType(userType: UserType) {
        viewModelScope.launch {
            dataStore.edit {
                it[userTypeKey] = userType.value
            }
        }
    }

    companion object {
        val userTypeKey = stringPreferencesKey(USER_TYPE_KEY)
        val defaultUserType = UserType.CITIZEN
    }

}