package com.example.project2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.project2.ui.theme.Project2Theme

class MainActivity : ComponentActivity() {
    private val animalRequestCode = 100
    private var animals = mutableStateListOf<Animal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ZooScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
        animals.addAll( //элементы по умолчанию
            listOf(
                Cat("Tygra", "White"),
                Frog("Frog", "Green"),
                Dog("Dog", "Brown"),
                Triton("Triton", "Blue")
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == animalRequestCode && resultCode == Activity.RESULT_OK) {
            val animal: Animal? = data?.getParcelableExtra("animal")
            animal?.let {
                //добавлем животное в  список
                animals.add(it)
            }
        }
    }

    //отображения экрана с животными
    @Composable
    fun ZooScreen(modifier: Modifier = Modifier) {
        var currentMessage by remember { mutableStateOf("") } //храним текущее сообщение
        val snackbarHostState = remember { SnackbarHostState() }
        var deleteMode by remember { mutableStateOf(false) } //режим удаления
        var selectedAnimals by remember { mutableStateOf(setOf<Animal>()) }



        LaunchedEffect(currentMessage) {
            if (currentMessage.isNotEmpty()) {
                snackbarHostState.showSnackbar(currentMessage)
                delay(3000)
                snackbarHostState.currentSnackbarData?.dismiss() //скрыть уведомление
            }
        }

        //обрабатываем удаление животных
        fun deleteSelectedAnimals() {
            animals.removeAll { it in selectedAnimals }
            selectedAnimals = emptySet()
            deleteMode = false
        }


        //экран с животными
        Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
            //спискок животных
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(animals) { animal ->
                    AnimalItem(
                        animal = animal,
                        onClick = {
                            if (!deleteMode) {
                                currentMessage = animal.speak()
                            }
                        },
                        onCheckedChange = {
                            //тут состояния чекбокса
                            selectedAnimals = if (it) {
                                selectedAnimals + animal
                            } else {
                                selectedAnimals - animal
                            }
                        },
                        isSelected = selectedAnimals.contains(animal),
                        deleteMode = deleteMode
                    )
                    Spacer(modifier = Modifier.height(2.dp)) //растояние между элементами
                }
            }


            if (!deleteMode) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
                ) {
                    //кнопка добавления
                    Button(
                        onClick = {
                            val intent =
                                Intent(this@MainActivity, AnimalSelectionActivity::class.java)
                            startActivityForResult(intent, animalRequestCode)
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
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
        }

        Box(modifier = Modifier.fillMaxSize()) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
//


    @Composable
    fun AnimalItem(
        animal: Animal,
        onClick: () -> Unit,
        onCheckedChange: (Boolean) -> Unit,
        isSelected: Boolean,
        deleteMode: Boolean
    ) {
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
