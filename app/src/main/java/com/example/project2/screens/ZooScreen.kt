package com.example.project2.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project2.db.AnimalsEntity
import com.example.project2.viewmodel.AnimalsViewModel

@Composable
fun ZooScreen(navController: NavController, animalsViewModel: AnimalsViewModel) {
    val animals by animalsViewModel.animalList.observeAsState(emptyList())
    var deleteMode by remember { mutableStateOf(false) }
    var selectedAnimals by remember { mutableStateOf(setOf<AnimalsEntity>()) }
    var showAnimalSelectionDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(animals) { animalEntity ->
                val animal = animalEntity.toAnimal() // Преобразование AnimalsEntity в Animal

                AnimalItem(
                    animal = animal,
                    onClick = {
                        if (!deleteMode) {
                            navController.navigate("animal_detail/${animalEntity.id}")
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

        // Кнопки управления
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            if (!deleteMode) {
                // Кнопка добавления животного
                Button(
                    onClick = { showAnimalSelectionDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("Add Animal", color = Color.White)
                }
            }

            // Кнопка для режима удаления
            Button(
                onClick = { deleteMode = !deleteMode },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(if (deleteMode) "Cancel" else "Delete Animals", color = Color.White)
            }
        }

        // Кнопка подтверждения удаления
        if (deleteMode && selectedAnimals.isNotEmpty()) {
            Button(
                onClick = {
                    animalsViewModel.selectedAnimalIds = selectedAnimals.map { it.id }
                    animalsViewModel.deleteSelectedAnimals()
                    deleteMode = false
                    selectedAnimals = emptySet()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Delete Selected Animals")
            }
        }

        // Диалог для добавления животного
        if (showAnimalSelectionDialog) {
            AnimalSelectionScreen(
                onSubmit = { animal ->
                    animalsViewModel.changeName(animal?.name ?: "")
                    animalsViewModel.changeColor(animal?.color ?: "")
                    // Передаем тип и форму животного, чтобы сохранить их
                    animalsViewModel.animalType = animal?.type ?: AnimalType.Cat // Передаем тип
                    animalsViewModel.animalForm =
                        animal?.form ?: AnimalForm.Mammal // Передаем форму
                    animalsViewModel.addAnimal() // Добавляем животное в базу данных
                    showAnimalSelectionDialog = false
                },
                onDismissRequest = { showAnimalSelectionDialog = false }
            )
        }
    }
}
