package com.example.project2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.project2.App
import com.example.project2.db.AnimalsDao
import com.example.project2.ui.theme.values.M
import com.example.project2.ui.theme.values.S
import com.example.project2.ui.theme.values.XXL
import com.example.project2.viewmodel.AnimalViewModel


@Composable
fun ZooScreen(
    navController: NavController//,
    //animalViewModel: AnimalViewModel//???
) {

    val animalsDao: AnimalsDao = App.animalsDao!!

    val animalViewModel: AnimalViewModel = remember { AnimalViewModel(animalsDao) }//LiveData
    // val animalViewModel: AnimalViewModel = viewModel() //Flow
    val state by animalViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(M)
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.animals) { animal ->
                AnimalItem(
                    animal = animal,
                    onClick = {
                        if (!state.deleteMode) {
                            navController.navigate("animal_detail/${animal.id}")
                        }
                    },
                    onCheckedChange = { isChecked ->
                        animalViewModel.selectAnimal(animal.id, isChecked)
                    },
                    isSelected = state.selectedAnimalIds.contains(animal.id),
                    deleteMode = state.deleteMode
                )
            }
        }

        // Кнопки управления
        Row(
            horizontalArrangement = Arrangement.spacedBy(M),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = XXL)
        ) {
            if (!state.deleteMode) {
                Button(
                    onClick = { animalViewModel.showAnimalSelectionDialog(true) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("Add Animal", color = Color.White)
                }
            }

            Button(
                onClick = { animalViewModel.toggleDeleteMode() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = if (state.deleteMode) Color.Gray else Color.Red)
            ) {
                Text(if (state.deleteMode) "Cancel" else "Delete Animals", color = Color.White)
            }
        }

        // Кнопка подтверждения удаления
        if (state.deleteMode) {
            Button(
                onClick = {
                    animalViewModel.deleteSelectedAnimals(state.selectedAnimalIds.toList())
                    animalViewModel.clearSelectedAnimals()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = S)
            ) {
                Text("Delete", color = Color.White)
            }
        }

        // Диалог добавления животного
        if (state.showAnimalSelectionDialog) {
            AnimalSelectionScreen(
                onSubmit = { animal ->
                    if (animal != null) {
                        animalViewModel.addAnimal(animal) // Добавление животного
                    }
                    animalViewModel.showAnimalSelectionDialog(false) // Закрытие диалога
                },
                onDismissRequest = { animalViewModel.showAnimalSelectionDialog(false) },
                animalViewModel = animalViewModel
            )
        }
    }
}