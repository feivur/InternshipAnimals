package com.example.project2

//import com.example.project2.screens.test.TestScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.project2.App.Companion.animalsDao
import com.example.project2.navigation.AppNavigation
import com.example.project2.viewmodel.AnimalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  Проверяем, что animalsDao не null
        val dao = animalsDao
        if (dao == null) {
            throw IllegalStateException("DAO is not initialized yet. Ensure App is initialized first.")
        }

        setContent {
            val navController = rememberNavController()

            val animalViewModel: AnimalViewModel = viewModel { AnimalViewModel(dao) }

            AppNavigation(navController = navController, animalViewModel = animalViewModel)//???
        }
    }
}