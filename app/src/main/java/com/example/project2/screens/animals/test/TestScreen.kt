package com.example.project2.screens.animals.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun TestScreen() {
    val model: TestViewModel = viewModel()
    val state by model.state.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(state.data)

        Button(onClick = { model.inc() }) {
            Text("increase")
        }
    }
}