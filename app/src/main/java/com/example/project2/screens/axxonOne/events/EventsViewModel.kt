package com.example.project2.screens.axxonOne.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.utils.ServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsViewModel : ViewModel() {

    private val _eventsState = MutableStateFlow(EventsState())
    val eventsState: StateFlow<EventsState> get() = _eventsState

    fun loadEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val events = ServerRepository.getEvents()
                withContext(Dispatchers.Main) {
                    _eventsState.value = _eventsState.value.copy(events = events)
                }
            } catch (e: Exception) {
                Log.e("EventsViewModel", "Error loading events: $e")
            }
        }
    }

}