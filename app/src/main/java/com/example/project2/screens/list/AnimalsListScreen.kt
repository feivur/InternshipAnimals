package com.example.project2.screens.list

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project2.structure.Animal
import com.example.project2.ui.theme.values.M
import com.example.project2.ui.theme.values.S
import com.example.project2.ui.theme.values.XXL


@Composable
fun ZooScreen(
    navController: NavController
) {
    val animalViewModel: AnimalsViewModel = viewModel()
    val state by animalViewModel.state.collectAsState()


    // Получаем новое животное из сохранённого состояния
    val newAnimal = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<Animal>("newAnimal")

    // Если новое животное есть, добавляем его
    LaunchedEffect(newAnimal) {
        newAnimal?.let {
            animalViewModel.addAnimal(it)
            navController.currentBackStackEntry?.savedStateHandle?.remove<Animal>("newAnimal")
        }
    }

    //
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
                Spacer(modifier = Modifier.height(2.dp))
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
                    onClick = { navController.navigate("animal_selection") },//
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
    }
}