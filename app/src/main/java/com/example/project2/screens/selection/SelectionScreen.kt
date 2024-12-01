package com.example.project2.screens.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.project2.structure.Animal
import com.example.project2.structure.Cat
import com.example.project2.structure.Dog
import com.example.project2.structure.Frog
import com.example.project2.structure.Triton

import com.example.project2.ui.theme.values.M
import com.example.project2.ui.theme.values.S

enum class AnimalType {
    Mammal, Reptile,
    Cat, Dog, Frog, Triton,
}


@Composable
fun AnimalSelectionScreen(
    onDismissRequest: () -> Unit,
    onSubmit: (Animal?) -> Unit
) {
    val selectionViewModel: SelectionViewModel = viewModel()
    val state by selectionViewModel.state.collectAsState()
    val isFormValid = state.name.isNotEmpty() && state.color.isNotEmpty()

    val animalMap = mapOf(
        AnimalType.Mammal to listOf(AnimalType.Cat, AnimalType.Dog),
        AnimalType.Reptile to listOf(AnimalType.Frog, AnimalType.Triton)
    )

    val currentAnimals =
        remember(state.selectedType) { animalMap[state.selectedType] ?: emptyList() }

    // Сбрасываем выбранное животное при смене типа
    LaunchedEffect(state.selectedType) {
        selectionViewModel.setSelectedAnimal(currentAnimals.firstOrNull() ?: AnimalType.Cat)
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(M)
                .background(Color.White)
        ) {
            // Выбор вида животного
            Text("Animal Type", style = MaterialTheme.typography.bodyLarge)
            animalMap.keys.forEach { type ->
                Row {
                    RadioButton(
                        selected = state.selectedType == type,
                        onClick = { selectionViewModel.setSelectedType(type) }
                    )
                    Text(type.name, modifier = Modifier.padding(start = S))
                }
            }

            Spacer(modifier = Modifier.padding(vertical = M))

            // Выбор типа животного в зависимости от вида
            Text("Animal", style = MaterialTheme.typography.bodyLarge)
            currentAnimals.forEach { animal ->
                Row {
                    RadioButton(
                        selected = state.selectedAnimal == animal,
                        onClick = { selectionViewModel.setSelectedAnimal(animal) }
                    )
                    Text(animal.name, modifier = Modifier.padding(start = S))
                }
            }

            Spacer(modifier = Modifier.padding(vertical = M))

            // Ввод имени животного
            TextField(
                value = state.name,
                onValueChange = { selectionViewModel.setName(it) },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = S)
            )

            // Ввод цвета животного
            TextField(
                value = state.color,
                onValueChange = { selectionViewModel.setColor(it) },
                label = { Text("Color") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = S)
            )

            // Кнопки подтверждения и отмены
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {////
                        val animal = when (state.selectedAnimal) {
                            AnimalType.Cat -> Cat(
                                id = System.currentTimeMillis(),
                                name = state.name,
                                color = state.color
                            )

                            AnimalType.Dog -> Dog(
                                id = System.currentTimeMillis(),
                                name = state.name,
                                color = state.color
                            )

                            AnimalType.Frog -> Frog(
                                id = System.currentTimeMillis(),
                                name = state.name,
                                color = state.color
                            )

                            AnimalType.Triton -> Triton(
                                id = System.currentTimeMillis(),
                                name = state.name,
                                color = state.color
                            )
                            else -> null
                        }////
                        onSubmit(animal)
                        selectionViewModel.clearForm()//
                    },
                    enabled = isFormValid,
                    modifier = Modifier
                        .weight(1f)
                        .padding(S)
                ) {
                    Text("Confirm")
                }
                Button(
                    onClick = { onDismissRequest() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(S)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
