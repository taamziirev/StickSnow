package com.project.sticksnow.presentation.event

import androidx.lifecycle.*
import com.project.data.common.Result
import com.project.data.entities.Event
import com.project.data.usecases.event.EventUseCase
import com.project.data.usecases.event.GetEventUseCase
import com.project.data.usecases.event.GetRemoteEventUseCase
import com.project.data.usecases.event.UnEventUseCase
import com.project.sticksnow.entities.EventInList
import com.project.sticksnow.entities.EventResult
import com.project.sticksnow.mappers.EventInListMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.ArrayList


class EventViewModel(
    private val getEventUseCase: GetEventUseCase,
    private val getRemoteEventUseCase: GetRemoteEventUseCase,
    private val eventUseCase: EventUseCase,
    private val unEventUseCase: UnEventUseCase,
    private val mapper: EventInListMapper
) : ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    var dataLoading: LiveData<Boolean> = _dataLoading

    private val _events = MutableLiveData<List<EventResult>>()
    val events = _events

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private lateinit var _remoteEvent : Event

    fun getEvents() {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            when (val eventResult = getRemoteEventUseCase.invoke()) {
                is Result.Success -> {
                    val array = ArrayList(_remoteEvent.eventResults)
                    array.clear()
                    array.addAll(eventResult.data)
                    _remoteEvent.eventResults = array

                    val eventFlow = getEventUseCase.invoke()
                    eventFlow.collect { eventz ->
                        events.value = mapper.fromEventToEventInList(_remoteEvent, eventz.map{ EventResult(it.id,it.description,it.title) })
                        _dataLoading.postValue(false)
                    }
                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    events.value = emptyList()
                    _error.postValue(eventResult.exception.message)
                }
            }
        }
    }

    fun event(event: EventInList) {
        viewModelScope.launch {
            eventUseCase.invoke(mapper.fromEventInListToEvent(event.eventResults))
        }
    }

    fun unEvent(event: EventInList) {
        viewModelScope.launch {
            unEventUseCase.invoke(mapper.fromEventInListToEvent(event.eventResults))
        }
    }

    class EventViewModelFactory(
        private val getEventVUseCase: GetEventUseCase,
        private val eventUseCase: EventUseCase,
        private val getRemoteEventUseCase: GetRemoteEventUseCase,
        private val unEventUseCase: UnEventUseCase,
        private val mapper: EventInListMapper
    ) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EventViewModel(
                getEventVUseCase,
                getRemoteEventUseCase,
                eventUseCase,
                unEventUseCase,
                mapper
            ) as T
        }
    }
}