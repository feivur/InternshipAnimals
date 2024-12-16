package com.example.project2.screens.axxonOne.events

import com.example.project2.structure.axxonOne.cameraEvents.Event

data class EventsState(
    val loading: Loading = Loading.Idle(),
    val events: List<Event> = emptyList()
)

sealed class Loading{
    class Progress(): Loading()

    sealed class Result(): Loading()

    class Idle(): Loading()

    class Success(): Result()

    class Error(reason: Exception): Result()
}
