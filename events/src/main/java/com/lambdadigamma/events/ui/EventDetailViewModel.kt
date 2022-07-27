package com.lambdadigamma.events.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lambdadigamma.core.Resource
import com.lambdadigamma.events.models.Event
import com.lambdadigamma.events.models.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    val eventRepository: EventRepository
) : ViewModel() {

    var eventId: Int? = null

    fun load(): LiveData<Resource<Event>> {

        return if (eventId != null) {
            Transformations.map(eventRepository.getEvent(eventId!!)) {
                return@map Resource.success(it)
            }
        } else {
            MutableLiveData(Resource.loading())
        }
        
    }

}