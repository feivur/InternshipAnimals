package com.example.project2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.project2.screens.AnimalDetailScreen
import com.example.project2.screens.ZooScreen

//import com.example.project2.viewmodel.AnimalViewModel

@Composable
fun AppNavigation(navController: NavHostController) {//, animalViewModel: AnimalViewModel) {//?
    NavHost(navController = navController, startDestination = "zoo_screen") {
        composable("zoo_screen") {
            ZooScreen(navController = navController)//, animalViewModel = animalViewModel)//?
        }
        composable(
            "animal_detail/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.LongType })
        ) { backStackEntry ->
            val animalId = backStackEntry.arguments?.getLong("animalId")
            AnimalDetailScreen(
                navController = navController,
                animalId = animalId
            )
        }
    }
}
