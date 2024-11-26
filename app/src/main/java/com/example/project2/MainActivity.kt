package com.example.project2

//import com.example.project2.screens.test.TestScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.project2.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  Проверяем, что animalsDao не null
//        val dao = animalsDao
//        if (dao == null) {
//            throw IllegalStateException("DAO is not initialized yet. Ensure App is initialized first.")
//        }

        setContent {
            val navController = rememberNavController()

            // val animalViewModel: AnimalsViewModel = viewModel { AnimalsViewModel() }

            AppNavigation(navController = navController)//, animalViewModel = animalViewModel)//???
        }
    }
}