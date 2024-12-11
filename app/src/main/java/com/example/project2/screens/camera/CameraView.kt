package com.example.project2.screens.camera

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project2.screens.axxonOne.data.ServerDataModel
import com.example.project2.structure.axxonOne.Camera
import com.example.project2.utils.Sizes.size_m

@Composable
fun CameraView(cameraId: String) {

    val serverModel: ServerDataModel = viewModel(key = "")
    val viewModel: CameraModel = viewModel(key = cameraId)

    val state by viewModel.state.collectAsState()

    LaunchedEffect(cameraId) {
        val camera = serverModel.getCamera(cameraId)
        viewModel.init(camera)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(size_m),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Camera: ${state.name}")

        state.bitmap?.let {
            Image(
                bitmap = state.bitmap!!.asImageBitmap(),
                contentDescription = "Camera Snapshot",
                modifier = Modifier.size(200.dp)
            )
        } ?: Text(text = "Loading snapshot...")
    }
}
