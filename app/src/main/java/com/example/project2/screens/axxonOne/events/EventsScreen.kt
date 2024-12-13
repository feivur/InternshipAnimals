package com.example.project2.screens.axxonOne.events

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project2.utils.Sizes.size_m
import com.example.project2.utils.Sizes.size_s

@Composable
fun EventsScreen() {
    val viewModel: EventsViewModel = viewModel()
    val eventsState by viewModel.eventsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadEvents()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(size_m)
    ) {
        items(eventsState.events) { event ->
            Text(
                text = "Событие: ${event.type}, Время: ${event.timestamp}",
                modifier = Modifier.padding(size_s)
            )
        }
    }
}
