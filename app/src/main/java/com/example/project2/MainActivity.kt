package com.example.project2

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
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
            val viewModelStoreOwner = LocalViewModelStoreOwner.current
            NavHost(navController = navController, startDestination = "server_version") {
                composable("server_version") {
                    CompositionLocalProvider(
                        LocalViewModelStoreOwner provides viewModelStoreOwner!!
                    ) {
                        ServerDataScreen(navController)
                    }
                }
                composable(
                    route = "camera_view/{cameraId}",
                    arguments = listOf(navArgument("cameraId") { type = NavType.StringType })
                ) { backStackEntry ->
                    CompositionLocalProvider(
                        LocalViewModelStoreOwner provides viewModelStoreOwner!!
                    ) {
                        // todo разобраться с Base64
                        // https://www.base64decode.org/
                        val cameraIdBase64 = backStackEntry.arguments?.getString("cameraId")!!
                        val cameraId = Base64.decode(cameraIdBase64, Base64.URL_SAFE).decodeToString()
                        CameraView(cameraId)
                    }
                }
            }
        }
    }
}
