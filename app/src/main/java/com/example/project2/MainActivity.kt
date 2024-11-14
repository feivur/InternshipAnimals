package com.example.project2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import com.example.project2.db.RoomDB
import com.example.project2.structure.Animal
import com.example.project2.structure.Cat
import com.example.project2.structure.Dog
import com.example.project2.structure.Frog
import com.example.project2.structure.Mammal
import com.example.project2.structure.Reptile
import com.example.project2.structure.Triton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var animalsDao: AnimalsDao
    private lateinit var db: RoomDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var isDatabaseInitialized by remember { mutableStateOf(false) }

            // инициализация бд
            LaunchedEffect(Unit) {
                initializeDatabase()
                isDatabaseInitialized = true
            }

            // показываем интерейс только после того, как база данных инициализировалась - скорее всего ошибка
            if (isDatabaseInitialized) {
                val animals: LiveData<List<AnimalsEntity>> = animalsDao.getAllAnimals()
                val animalsList by animals.observeAsState(emptyList())

                //навигция
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "zoo_screen") {
                    composable("zoo_screen") {
                        ZooScreen(navController = navController, animals = animalsList)
                    }
                    composable(
                        "animal_detail/{animalName}",
                        arguments = listOf(navArgument("animalName") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val animalName = backStackEntry.arguments?.getString("animalName") ?: ""
                        val animal =
                            animalsList.find { it.name == animalName }?.let { animalEntity ->
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
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }

    // инициализация бд
    private suspend fun initializeDatabase() {
        withContext(Dispatchers.IO) {
            db = Room.databaseBuilder(applicationContext, RoomDB::class.java, "animals_db").build()
            animalsDao = db.animalsDao()
        }
    }

    //

    @Composable
    fun ZooScreen(navController: NavController, animals: List<AnimalsEntity>) {
        var deleteMode by remember { mutableStateOf(false) }
        var selectedAnimals by remember { mutableStateOf(setOf<AnimalsEntity>()) }
        var showAnimalSelectionDialog by remember { mutableStateOf(false) }
        var showDeleteConfirmation by remember { mutableStateOf(false) }
        var isDeleting by remember { mutableStateOf(false) }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(animals) { animalEntity ->
                    val animal = when (animalEntity.type) {
                        "Cat" -> Cat(animalEntity.name!!, animalEntity.color!!)
                        "Dog" -> Dog(animalEntity.name!!, animalEntity.color!!)
                        "Frog" -> Frog(animalEntity.name!!, animalEntity.color!!)
                        "Triton" -> Triton(animalEntity.name!!, animalEntity.color!!)
                        else -> throw IllegalArgumentException("UnknownType")
                    }

                    AnimalItem(
                        animal = animal,
                        onClick = {
                            if (!deleteMode) {
                                navController.navigate("animal_detail/${animal.name}")
                            }
                        },
                        onCheckedChange = { isChecked ->
                            selectedAnimals = if (isChecked) {
                                selectedAnimals + animalEntity
                            } else {
                                selectedAnimals - animalEntity
                            }
                        },
                        isSelected = selectedAnimals.contains(animalEntity),
                        deleteMode = deleteMode
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }

            //ок и отмена только в режиме удаленя
            if (deleteMode) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                ) {
                    Button(
                        onClick = {
                            if (!isDeleting) {
                                isDeleting = true
                                showDeleteConfirmation = true
                                Log.d(
                                    "ZooScreen",
                                    "Нажал на подтвердить удаление. Животные выбраны: ${selectedAnimals.map { it.name }}"
                                )
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        enabled = !isDeleting
                    ) {
                        Text("Confirm", color = Color.White)
                    }

                    Button(
                        onClick = {
                            deleteMode = false
                            selectedAnimals = emptySet()
                            Log.d("ZooScreen", "Отмена нажата.")
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                ) {
                    Button(
                        onClick = { showAnimalSelectionDialog = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text("Add Animal", color = Color.White)
                    }

                    Button(
                        onClick = {
                            deleteMode = !deleteMode
                            Log.d("ZooScreen", "Режим удаления переключен. Теперь он: $deleteMode")
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Delete Animals", color = Color.White)
                    }
                }
            }

            // подтверждение удаления
            if (showDeleteConfirmation) {
                LaunchedEffect(selectedAnimals) {
                    val selectedIds = selectedAnimals.map { it.id!! }  //сохраняем выбранные ID
                    Log.d("ZooScreen", "Начинаю удаление выбранных: $selectedIds")

                    // проверка, которая нужна была для отладки
                    if (selectedIds.isNotEmpty()) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            animalsDao.deleteAnimals(selectedIds)  // удаляем по ID
                            Log.d("ZooScreen", "Удаление выбранных животных завершено!!!")
                        }
                    } else {
                        Log.d("ZooScreen", "Животные для удаления не выбраны!")
                    }

                    // сброс состояния после удаления
                    deleteMode = false
                    selectedAnimals = emptySet()
                    showDeleteConfirmation = false
                    isDeleting = false
                }
            }


            if (showAnimalSelectionDialog) {
                AnimalSelectionScreen(
                    onSubmit = { animal ->
                        animal?.let {
                            addAnimalToDatabase(it)
                        }
                        showAnimalSelectionDialog = false
                    },
                    onDismissRequest = { showAnimalSelectionDialog = false }
                )
            }
        }
    }


    private fun addAnimalToDatabase(animal: Animal) {
        val animalEntity = AnimalsEntity(
            name = animal.name,
            color = animal.color,
            type = when (animal) {
                is Cat -> "Cat"
                is Dog -> "Dog"
                is Frog -> "Frog"
                is Triton -> "Triton"
                else -> "Unknown"
            }
        )

        lifecycleScope.launch(Dispatchers.IO) {
            animalsDao.insertAnimal(animalEntity)
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AnimalDetailScreen(navController: NavController, animal: Animal) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Animal Details") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)) {
                Text(
                    text = "Animal Type: ${if (animal is Mammal) "Mammal" else "Reptile"}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Animal: ${animal::class.simpleName}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Name: ${animal.name}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Color: ${animal.color}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp
                    )
                )
            }
        }
    }

    @Composable
    fun AnimalItem(animal: Animal, onClick: () -> Unit, onCheckedChange: (Boolean) -> Unit, isSelected: Boolean, deleteMode: Boolean) {
        val backgroundColor = when (animal) {
            is Mammal -> Color(0xFFFF9800)
            is Reptile -> Color(0xFF03A9F4)
            else -> Color.LightGray
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(8.dp)
                .clickable { onClick() }
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (deleteMode) {
                        Checkbox(checked = isSelected, onCheckedChange = onCheckedChange)
                    }
                    Text(
                        text = animal.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    )
                }
                Text(
                    text = "Color - ${animal.color}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}