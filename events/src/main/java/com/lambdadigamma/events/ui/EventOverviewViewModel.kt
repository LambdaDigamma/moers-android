package com.lambdadigamma.events.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdadigamma.core.Resource
import com.lambdadigamma.events.models.Event
import com.lambdadigamma.events.models.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventOverviewViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    fun load(): LiveData<Resource<List<Event>?>> {
        return eventRepository.getEvents()
    }

}