package com.example.project2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import com.example.project2.db.RoomDB
import com.example.project2.navigation.AppNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var animalsDao: AnimalsDao
    private lateinit var db: RoomDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDatabaseInitialized by remember { mutableStateOf(false) }
            // Инициализация БД
            LaunchedEffect(Unit) {
                initializeDatabase()
                isDatabaseInitialized = true
            }
            // Показываем интерфейс после инициализации БД
            if (isDatabaseInitialized) {
                val animals: LiveData<List<AnimalsEntity>> = animalsDao.getAllAnimals()
                // Навигация, передаем animalsDao в ZooScreen
                AppNavigation(
                    animals = animals,
                    animalsDao = animalsDao
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }

    // Инициализация БД
    private suspend fun initializeDatabase() {
        withContext(Dispatchers.IO) {
            db = Room.databaseBuilder(applicationContext, RoomDB::class.java, "animals_db").build()
            animalsDao = db.animalsDao()
        }
    }
}
