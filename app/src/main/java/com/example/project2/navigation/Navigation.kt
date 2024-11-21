package com.example.project2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.project2.screens.AnimalDetailScreen
import com.example.project2.screens.ZooScreen
import com.example.project2.viewmodel.AnimalsViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    animalsViewModel: AnimalsViewModel
) {
    //val animals by animalsViewModel.animalList.observeAsState(emptyList())//todo убрать  +

    NavHost(navController = navController, startDestination = "zoo_screen") {
        composable("zoo_screen") {
            ZooScreen(
                navController = navController,
                animalsViewModel = animalsViewModel
            )
        }// что будет, если animalName повторится у другого итема?
        composable(
            "animal_detail/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.LongType })
        ) { backStackEntry ->
            val animalId = backStackEntry.arguments?.getLong("animalId")
            AnimalDetailScreen(
                navController = navController,
                animalId = animalId,
                animalsViewModel = animalsViewModel
            )
        }
    }
}

