package com.example.project2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import com.example.project2.screens.AnimalDetailScreen
import com.example.project2.screens.ZooScreen
import com.example.project2.structure.Cat
import com.example.project2.structure.Dog
import com.example.project2.structure.Frog
import com.example.project2.structure.Triton

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    animals: LiveData<List<AnimalsEntity>>,
    animalsDao: AnimalsDao // Добавляем animalsDao как параметр
) {
    val animalsList by animals.observeAsState(emptyList())

    NavHost(navController = navController, startDestination = "zoo_screen") {
        composable("zoo_screen") {
            // Передаем animalsDao в ZooScreen
            ZooScreen(
                navController = navController,
                animals = animalsList,
                animalsDao = animalsDao // Передаем сюда animalsDao
            )
        }
        composable(
            "animal_detail/{animalName}",
            arguments = listOf(navArgument("animalName") { type = NavType.StringType })
        ) { backStackEntry ->
            val animalName = backStackEntry.arguments?.getString("animalName")
            val animal = animalsList.find { it.name == animalName }?.let { animalEntity ->
                when (animalEntity.type) {
                    "Cat" -> Cat(animalEntity.name!!, animalEntity.color!!)
                    "Dog" -> Dog(animalEntity.name!!, animalEntity.color!!)
                    "Frog" -> Frog(animalEntity.name!!, animalEntity.color!!)
                    "Triton" -> Triton(animalEntity.name!!, animalEntity.color!!)
                    else -> throw IllegalArgumentException("UnknownType")
                }
            }
            animal?.let {
                AnimalDetailScreen(navController = navController, animal = it)
            }
        }
    }
}
