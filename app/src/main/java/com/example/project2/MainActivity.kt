package com.example.project2

//import android.app.Activity
//import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project2.ui.theme.Project2Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val animalRequestCode = 100
    private var animals = mutableStateListOf<Animal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project2Theme {
                val navController = rememberNavController()//todo навигация
                NavHost(navController, startDestination = "zoo_screen") {
                    composable("zoo_screen") {
                        ZooScreen(navController = navController)
                    }
                    composable("animal_detail/{animalName}") { backStackEntry ->
                        val animalName = backStackEntry.arguments?.getString("animalName")
                        val animal = animals.find { it.name == animalName }
                        animal?.let {
                            AnimalDetailScreen(navController = navController, animal = it)
                        }
                    }
                }
            }
        }
        animals.addAll( // элементы по умолчанию
            listOf(
                Cat("Tygra", "White"),
                Frog("Alt", "Green"),
                Dog("Del", "Brown"),
                Triton("Splash", "Blue")
            )
        )
    }

    // отображение экрана с животными
    @Composable
    fun ZooScreen(navController: NavController) {
        var currentMessage by remember { mutableStateOf("") } // храним текущее сообщение
        val snackbarHostState = remember { SnackbarHostState() }
        var deleteMode by remember { mutableStateOf(false) } // режим удаления
        var selectedAnimals by remember { mutableStateOf(setOf<Animal>()) }
        var showAnimalSelectionDialog by remember { mutableStateOf(false) }

        LaunchedEffect(currentMessage) {
            if (currentMessage.isNotEmpty()) {
                snackbarHostState.showSnackbar(currentMessage)
                delay(3000)
                snackbarHostState.currentSnackbarData?.dismiss() // скрыть уведомление
            }
        }

        fun deleteSelectedAnimals() {
            animals.removeAll { it in selectedAnimals }
            selectedAnimals = emptySet()
            deleteMode = false
        }

        // экран с животными
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            // список животных
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(animals) { animal ->
                    AnimalItem(
                        animal = animal,
                        onClick = {
                            if (!deleteMode) {
                                navController.navigate("animal_detail/${animal.name}")
                            }
                        },
                        onCheckedChange = {
                            selectedAnimals = if (it) {
                                selectedAnimals + animal
                            } else {
                                selectedAnimals - animal
                            }
                        },
                        isSelected = selectedAnimals.contains(animal),
                        deleteMode = deleteMode
                    )
                    Spacer(modifier = Modifier.height(2.dp)) // расстояние между элементами списка
                }
            }

            if (!deleteMode) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                ) {
                    //кнопка добавления
                    Button(
                        onClick = {
                            showAnimalSelectionDialog = true
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text("Add Animal", color = Color.White)
                    }

                    //режим удаления
                    Button(
                        onClick = { deleteMode = !deleteMode },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Delete Animals", color = Color.White)
                    }
                }
            }

            if (deleteMode) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                ) {
                    Button(
                        onClick = { deleteSelectedAnimals() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Delete", color = Color.White)
                    }

                    Button(
                        onClick = { deleteMode = false },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                }
            }

            if (showAnimalSelectionDialog) {
                AnimalSelectionScreen(
                    onSubmit = { animal ->
                        animal?.let { animals.add(it) }
                        showAnimalSelectionDialog = false
                    },
                    onDismissRequest = { showAnimalSelectionDialog = false }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    //todo экранчик с просмотрлм описания
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
        ) { innerPadding -> //  передаем отсткпы
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Animal Type: ${if (animal is Mammal) "Mammal" else "Reptile"}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp

                    )
                )
                Spacer(modifier = Modifier.height(8.dp)) //отступы
                Text(
                    text = "Animal: ${
                        when (animal) {
                            is Cat -> "Cat"
                            is Dog -> "Dog"
                            is Frog -> "Frog"
                            is Triton -> "Triton"
                            else -> "Unknown"
                        }
                    }",
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
                        fontSize = 20.sp // размер текста
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

//

    @Composable
    fun AnimalItem(animal: Animal, onClick: () -> Unit, onCheckedChange: (Boolean) -> Unit, isSelected: Boolean, deleteMode: Boolean) {
        //цвет фона в зависиости от типа
        val backgroundColor = when (animal) {
            is Mammal -> Color(0xFFFF9800)  //млекопит
            is Reptile -> Color(0xFF03A9F4)  //репт
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
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = onCheckedChange
                        )
                    }
                    Text(
                        text = animal.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold, //жирным имя
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    )
                }
                             Text(
                    text = "Color - ${animal.color}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = animal.describe(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontStyle = FontStyle.Italic //курсивом описание
                    )
                )
                //ставимм галочку если чекбокч сработал
                if (isSelected) {
                    Text(
                        text = "✔️ Selected",
                        color = Color.Green,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

