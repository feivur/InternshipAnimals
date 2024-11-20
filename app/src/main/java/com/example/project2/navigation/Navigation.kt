package com.example.project2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
    val animals by animalsViewModel.animalList.observeAsState(emptyList())
//todo в навигации не место внешней логике. Она лишь роутер экранов
    NavHost(navController = navController, startDestination = "zoo_screen") {
        composable("zoo_screen") {
            ZooScreen(
                navController = navController,
                animalsViewModel = animalsViewModel
            )
        }//todo что будет, если animalName повторится у другого итема?
        composable(
            "animal_detail/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.LongType })
        ) { backStackEntry ->
            val animalId = backStackEntry.arguments?.getLong("animalId")
            val animal = animals.find { it.id == animalId }

            animal?.let {
                AnimalDetailScreen(navController = navController, animal = it)
            }
        }
    }
}

