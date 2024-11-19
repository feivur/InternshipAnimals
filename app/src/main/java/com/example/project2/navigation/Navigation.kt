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
    //todo в навигации не место внешней логике. Она лишь роутер экранов
    val animalsList by animalsViewModel.allAnimals.observeAsState(emptyList())

    NavHost(navController = navController, startDestination = "zoo_screen") {
        composable("zoo_screen") {
            ZooScreen(
                navController = navController,
                animalsViewModel = animalsViewModel
            )
        }
        //todo что будет, если animalName повторится у другого итема?
        composable(
            "animal_detail/{animalName}",
            arguments = listOf(navArgument("animalName") { type = NavType.StringType })
        ) { backStackEntry ->
            val animalName = backStackEntry.arguments?.getString("animalName")
            val animal = animalsList.find { it.name == animalName }
            animal?.let {
                AnimalDetailScreen(navController = navController, animal = it)
            }
        }
    }
}
