package com.example.project2.screens.axxonOne.events

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project2.utils.Sizes.size_m
import com.example.project2.utils.Sizes.size_s

@Composable
fun EventsScreen() {
    val viewModel: EventsViewModel = viewModel()
    val eventsState by viewModel.eventsState.collectAsState()

    var beginTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var limit by remember { mutableStateOf("") }
    var offset by remember { mutableStateOf("") }
    var limitToArchive by remember { mutableStateOf(false) }

    var showBeginDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var finalUrl by remember { mutableStateOf("") }    //итог



    Column(modifier = Modifier.padding(size_m)) {
//выбор начального времени
        Button(
            onClick = { showBeginDatePicker = true },
            modifier = Modifier.padding(vertical = size_s)
        ) {
            Text(if (beginTime.isEmpty()) "Выбрать начальное время" else "Начальное время: $beginTime")
        }

//выбор конечного времени
        Button(
            onClick = { showEndDatePicker = true },
            modifier = Modifier.padding(vertical = size_s)
        ) {
            Text(if (endTime.isEmpty()) "Выбрать конечное время" else "Конечное время: $endTime")
        }

//лимит
        TextField(
            value = limit,
            onValueChange = { limit = it },
            label = { Text("Лимит (например, 10)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = size_s)
        )
//смещения
        TextField(
            value = offset,
            onValueChange = { offset = it },
            label = { Text("Смещение (например, 0)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = size_s)
        )

//архив вкл выкл
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = size_s)
        ) {
            Text("Только архив")
            Switch(checked = limitToArchive, onCheckedChange = { limitToArchive = it })
        }

//для поискв
        Button(
            onClick = {
                if (beginTime.isNotEmpty() && endTime.isNotEmpty()) {
                    isLoading = true
                    errorMessage = ""
                    finalUrl = "http://try.axxonsoft.com:8000/asip-api/" +
                            "archive/events/detectors/$endTime/$beginTime?limit=" +
                            "${limit.ifEmpty { "10" }}&offset=${offset.ifEmpty { "0" }}&limit_to_archive=" +
                            "${if (limitToArchive) 1 else 0}"


                    viewModel.loadEvents(
                        endTime = endTime,
                        beginTime = beginTime,
                        limit = limit.toIntOrNull() ?: 10,
                        offset = offset.toIntOrNull() ?: 0,
                        limitToArchive = if (limitToArchive) 1 else 0
                    ) {

                        isLoading = false
                        if (it.isEmpty()) errorMessage = "События не найдены."
                    }
                } else {
                    errorMessage = "Пожалуйста, заполните все поля."
                }
            },
            modifier = Modifier.padding(vertical = size_s)
        ) {
            Text("Искать")
        }

//URL в итоге
        if (finalUrl.isNotEmpty()) {
            Text(
                "URL: $finalUrl",
                modifier = Modifier.padding(vertical = size_s)
            )
        }

//индикатор загрузки
        if (isLoading) {
            LoadingAnimation()
        }

//показ ошибки
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage, color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = size_s)
            )
        }

//список событий
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(eventsState.events) { event ->
                Text(
                    text = "Событие: ${event.type}, Время: ${event.timestamp}",
                    modifier = Modifier.padding(size_s)
                )
            }
        }
    }


//DateTimePicker для начального времени
    if (showBeginDatePicker) {
        DateTimePicker(
            onDismiss = { showBeginDatePicker = false },
            onDateTimeSelected = { dateTime ->
                beginTime = dateTime
                showBeginDatePicker = false
            }
        )
    }

//для конечного времени
    if (showEndDatePicker) {
        DateTimePicker(
            onDismiss = { showEndDatePicker = false },
            onDateTimeSelected = { dateTime ->
                endTime = dateTime
                showEndDatePicker = false
            }
        )
    }
}


//pfuheprf
@Composable
fun LoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),//10 сек
            //repeatMode = RepeatMode.Restart
        )
    )

    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .padding(size_s)
    )
}