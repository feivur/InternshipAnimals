package com.example.project2.screens.camera

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project2.screens.axxonOne.data.ServerDataModel
import com.example.project2.structure.axxonOne.Camera

@Composable
fun CameraView(camera: Camera){
    val viewModel: CameraModel = viewModel(key = camera.displayId)

    //todo view : name, id, image
    val state by viewModel.state.collectAsState()

    DisposableEffect(camera) {
        //todo init(camera) model
        onDispose {}
    }

    if (state.bitmap != null) {
        Image(
            bitmap = state.bitmap!!.asImageBitmap(),
            contentDescription = null
        )
    }
}