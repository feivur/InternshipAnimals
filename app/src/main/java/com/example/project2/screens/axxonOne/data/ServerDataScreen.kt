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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.project2.structure.axxonOne.Camera
import com.example.project2.utils.Sizes.size_m
import com.example.project2.utils.Sizes.size_s
import com.example.project2.utils.Sizes.text_size_l

@Composable
fun ServerDataScreen(viewModel: ServerDataModel = viewModel()) {
    val serverDataState by viewModel.serverDataState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadServerVersion()
        viewModel.loadCameras()
    }

    //todo
/*
LazyColumn{
    items(cameras){camera ->
        CameraView(camera)
    }
}
*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(size_m),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Server Version: ${serverDataState.version}",
            modifier = Modifier.padding(size_s),
            fontSize = text_size_l
        )

        Text(text = "Cameras: ${serverDataState.cameraCount}")

        serverDataState.cameras.forEach { cameraWithSnapshot ->
            cameraWithSnapshot.snapshotUrl?.let { snapshotUrl ->
                AsyncImage(
                    model = snapshotUrl,
                    contentDescription = "Snapshot for ${cameraWithSnapshot.camera.displayName}",
                    modifier = Modifier
                        .padding(size_s)
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

