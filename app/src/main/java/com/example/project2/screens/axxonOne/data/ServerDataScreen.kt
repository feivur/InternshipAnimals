package com.example.project2.screens.axxonOne.data

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun ServerDataScreen(viewModel: ServerDataModel = viewModel()) {
    val serverDataState by viewModel.serverDataState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadServerVersion()
        viewModel.loadCameras()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Server Version: ${serverDataState.version}",
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp
        )

        Text(text = "Cameras: ${serverDataState.cameraCount}")

        serverDataState.cameras.forEach { cameraWithSnapshot ->
            cameraWithSnapshot.snapshotUrl?.let { snapshotUrl ->
                AsyncImage(
                    model = snapshotUrl,
                    contentDescription = "Snapshot for ${cameraWithSnapshot.camera.displayName}",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(200.dp),
                    onError = { exception ->
                        Log.e(
                            "ImageLoad",
                            "Error loading snapshot for ${cameraWithSnapshot.camera.displayName}: $exception"
                        )
                    }
                )
            } ?: run {
                Text(text = "Error loading snapshot for ${cameraWithSnapshot.camera.displayName}.")
            }
        }
    }
}
