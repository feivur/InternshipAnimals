package com.example.project2

//import com.example.project2.screens.test.TestScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.project2.navigation.AppNavigation
import com.example.project2.viewmodel.AnimalsViewModel
import com.example.project2.viewmodel.AnimalsViewModelFactory


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = AnimalsViewModelFactory(application)
        val animalsViewModel: AnimalsViewModel by viewModels { viewModelFactory }

        setContent {
            val navController = rememberNavController()
            AppNavigation(
                animalsViewModel = animalsViewModel,
                navController = navController
            )
        }
    }
}