package com.example.project2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project2.screens.axxonOne.data.ServerDataModel
import com.example.project2.screens.axxonOne.data.ServerDataScreen
import com.example.project2.screens.camera.CameraView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val serverDataModel: ServerDataModel = viewModel()

            NavHost(navController = navController, startDestination = "server_version") {
                composable("server_version") {
                    ServerDataScreen { camera ->
                        navController.navigate("camera_view/${camera.displayId}")
                    }
                }
                composable(
                    route = "camera_view/{cameraId}",
                    arguments = listOf(navArgument("cameraId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val cameraId = backStackEntry.arguments?.getString("cameraId")!!
                    val cameraWithSnapshot = serverDataModel.serverDataState.value.cameras
                        .find { it.camera.displayId == cameraId }
                        ?: throw RuntimeException("Camera not found")
                    CameraView(camera = cameraWithSnapshot.camera)
                }
            }
        }
    }
}
