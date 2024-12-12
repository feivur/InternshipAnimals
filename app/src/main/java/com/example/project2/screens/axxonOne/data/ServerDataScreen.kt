package com.example.project2.screens.axxonOne.data

import android.util.Base64
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project2.structure.axxonOne.Camera
import com.example.project2.utils.Sizes.size_m
import com.example.project2.utils.Sizes.size_s
import com.example.project2.utils.Sizes.text_size_l


@Composable
fun ServerDataScreen(
    navController: NavController
) {
    val mso = LocalViewModelStoreOwner.current
    val viewModel: ServerDataModel = viewModel(key = "")
    val serverDataState by viewModel.serverDataState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadServerVersion()
        viewModel.loadCameras()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Server Version: ${serverDataState.version}",
            modifier = Modifier.padding(size_m),
            fontSize = text_size_l
        )
//todo LazyColumn{
//    items(cameras){camera ->
//        CameraView(camera)
//    }
        LazyColumn(modifier = Modifier.padding(size_m)) {
            itemsIndexed(serverDataState.cameras) { index, cameraWithSnapshot ->
                val camera = cameraWithSnapshot.camera
                Text(
                    text = "${index + 1}. ${camera.displayName}",
                    modifier = Modifier
                        .padding(size_s)
                        .clickable {
                            val cameraIdBase64 = Base64.encode(camera.id().toByteArray(), Base64.URL_SAFE).decodeToString()
                            navController.navigate("camera_view/${cameraIdBase64}")
                            //onCameraClick(camera)
                        }
                )
            }
        }
    }
}
