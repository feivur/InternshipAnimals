package com.example.project2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.project2.screens.details.AnimalDetailScreen
import com.example.project2.screens.list.ZooScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "zoo_screen") {
        composable("zoo_screen") {
            ZooScreen(navController = navController)
        }
        composable(
            "animal_detail/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.LongType })
        ) { backStackEntry ->
            val animalId = backStackEntry.arguments?.getLong("animalId")
            if (animalId != null) {
                AnimalDetailScreen(
                    navController = navController,
                    animalId = animalId
                )
            }
        }
        //todo remove from nav +
    }
}
