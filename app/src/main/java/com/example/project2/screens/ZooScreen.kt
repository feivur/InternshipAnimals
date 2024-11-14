package com.example.project2.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import com.example.project2.functions.addAnimal
import com.example.project2.structure.Cat
import com.example.project2.structure.Dog
import com.example.project2.structure.Frog
import com.example.project2.structure.Triton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ZooScreen(
    navController: NavController,
    animals: List<AnimalsEntity>,
    animalsDao: AnimalsDao // Получаем animalsDao
) {
    var deleteMode by remember { mutableStateOf(false) }
    var selectedAnimals by remember { mutableStateOf(setOf<AnimalsEntity>()) }
    var showAnimalSelectionDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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

        // Кнопки для подтверждения и отмены удаления
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

        // Подтверждение удаления
        if (showDeleteConfirmation) {
            LaunchedEffect(selectedAnimals) {
                val selectedIds = selectedAnimals.map { it.id!! } // Сохраняем выбранные ID
                Log.d("ZooScreen", "Начинаю удаление выбранных: $selectedIds")

                if (selectedIds.isNotEmpty()) {
                    lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                        animalsDao.deleteAnimals(selectedIds) // Удаляем по ID
                        Log.d("ZooScreen", "Удаление выбранных животных завершено!!!")
                    }
                } else {
                    Log.d("ZooScreen", "Животные для удаления не выбраны!")
                }

                // Сброс состояния после удаления
                deleteMode = false
                selectedAnimals = emptySet()
                showDeleteConfirmation = false
                isDeleting = false
            }
        }

        // Диалог для добавления животного
        if (showAnimalSelectionDialog) {
            AnimalSelectionScreen(
                onSubmit = { animal ->
                    animal?.let {
                        addAnimal(
                            it,
                            animalsDao = animalsDao,
                            lifecycleScope = lifecycleOwner.lifecycleScope
                        )
                    }
                    showAnimalSelectionDialog = false
                },
                onDismissRequest = { showAnimalSelectionDialog = false }
            )
        }
    }
}
