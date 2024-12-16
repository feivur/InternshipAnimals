package com.example.project2.screens.axxonOne.camera

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.project2.screens.axxonOne.data.ServerDataModel
import com.example.project2.utils.Sizes.size_large
import com.example.project2.utils.Sizes.size_m
import okio.ByteString.Companion.decodeHex
import java.nio.charset.Charset
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun CameraView(cameraIdHex: String) {

    val serverModel: ServerDataModel = hiltViewModel()//viewModel(key = "")
    val viewModel: CameraModel = hiltViewModel()//viewModel(key = cameraId)

    val state by viewModel.state.collectAsState()

    LaunchedEffect(cameraIdHex) {
        val cameraId = cameraIdHex.decodeHex().string(Charset.defaultCharset())
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
                modifier = Modifier.size(size_large)
            )
        } ?: Text(text = "Loading snapshot...")
    }
}
