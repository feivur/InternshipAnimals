package com.example.project2.navigation.axxonOne

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.project2.screens.axxonOne.camera.CameraView
import com.example.project2.screens.axxonOne.data.ServerDataScreen
import com.example.project2.screens.axxonOne.events.EventsScreen
//import kotlin.io.decoding.Base64
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object AppNavigation {

    @OptIn(ExperimentalEncodingApi::class)
    @Composable
    fun SetupNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "server_version") {
            composable("server_version") {
                ServerDataScreen(navController)
            }
            composable(
                route = "camera_view/{cameraId}",
                arguments = listOf(navArgument("cameraId") { type = NavType.StringType })
            ) { backStackEntry ->
                val cameraIdBase64 = backStackEntry.arguments?.getString("cameraId")!!
                val cameraId =
                    Base64.decode(cameraIdBase64)//, Base64.URL_SAFE)//- по идее он сам должен справляться с превращением / в _
                        .decodeToString()
                CameraView(cameraId)
            }
            composable("events_screen") {
                EventsScreen()
            }
        }
    }
}
