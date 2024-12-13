package com.example.project2.server.events

import com.example.project2.structure.axxonOne.cameraEvents.Event

data class EventsResponse(
    val events: List<Event>,
    val more: Boolean
)
