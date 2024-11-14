package com.example.project2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.project2.db.RoomDB
import com.example.project2.navigation.AppNavigation
import com.example.project2.viewmodel.AnimalsViewModel
import com.example.project2.viewmodel.AnimalsViewModelFactory


class MainActivity : ComponentActivity() {
    private lateinit var db: RoomDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация базы данных
        db = Room.databaseBuilder(applicationContext, RoomDB::class.java, "animals_db").build()

        // Создание ViewModelFactory для передачи animalsDao
        val animalsDao = db.animalsDao()
        val viewModelFactory = AnimalsViewModelFactory(application, animalsDao)
        val animalsViewModel: AnimalsViewModel by viewModels { viewModelFactory }

        setContent {
            // Создание NavController
            val navController = rememberNavController()

            // LiveData автоматически наблюдается
            val animals by animalsViewModel.allAnimals.observeAsState(emptyList())

            if (animals.isEmpty()) {
                // Используем Box для выравнивания индикатора
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                AppNavigation(
                    animalsViewModel = animalsViewModel,
                    navController = navController
                )
            }
        }
    }
}