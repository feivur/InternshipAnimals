package com.example.project2.screens.axxonOne.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.structure.axxonOne.cameraEvents.Event
import com.example.project2.utils.ServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsViewModel : ViewModel() {

    private val _eventsState = MutableStateFlow(EventsState())
    val eventsState: StateFlow<EventsState> get() = _eventsState

    fun loadEvents(
        endTime: String,
        beginTime: String,
        limit: Int,
        offset: Int,
        limitToArchive: Int,
        onResult: (List<Event>) -> Unit
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val events = ServerRepository.getEvents(
                    endTime = endTime,
                    beginTime = beginTime,
                    limit = limit,
                    offset = offset,
                    limitToArchive = limitToArchive
                )
                withContext(Dispatchers.Main) {
                    _eventsState.value = _eventsState.value.copy(events = events)
                    onResult(events)
                }
            } catch (e: Exception) {
                Log.e("EventsViewModel", "Error loading events: $e")
                withContext(Dispatchers.Main) {
                    onResult(emptyList())
                }
            }
        }
    }
}
