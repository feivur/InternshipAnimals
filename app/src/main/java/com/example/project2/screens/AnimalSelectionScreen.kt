package com.example.project2.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import com.example.project2.structure.Animal
import com.example.project2.structure.Cat
import com.example.project2.structure.Dog
import com.example.project2.structure.Frog
import com.example.project2.structure.Triton
import com.example.project2.ui.theme.values.M
import com.example.project2.ui.theme.values.S

//enum class AnimalForm {
//    Mammal, Reptile
//}
enum class AnimalType {
    Mammal, Reptile,
    Cat, Dog, Frog, Triton,
}
@Composable
fun AnimalSelectionScreen(
    onDismissRequest: () -> Unit,
    onSubmit: (Animal?) -> Unit
) {
    var selectedType by rememberSaveable { mutableStateOf(AnimalType.Mammal) }
    var selectedAnimal by rememberSaveable { mutableStateOf(AnimalType.Cat) }
    var name by rememberSaveable { mutableStateOf("") }
    var color by rememberSaveable { mutableStateOf("") }
    val isFormValid = name.isNotEmpty() && color.isNotEmpty()

    val animalMap = mapOf(
        AnimalType.Mammal to listOf(AnimalType.Cat, AnimalType.Dog),
        AnimalType.Reptile to listOf(AnimalType.Frog, AnimalType.Triton)
    )

    val currentAnimals = remember(selectedType) { animalMap[selectedType] ?: emptyList() }

    // Сбрасываем выбранное животное при смене типа
    LaunchedEffect(selectedType) {
        selectedAnimal = currentAnimals.firstOrNull() ?: AnimalType.Cat
    }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
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
                        selected = selectedType == type,
                        onClick = { selectedType = type }
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
                        selected = selectedAnimal == animal,
                        onClick = { selectedAnimal = animal }
                    )
                    Text(animal.name, modifier = Modifier.padding(start = S))
                }
            }

            Spacer(modifier = Modifier.padding(vertical = M))

            // Ввод имени животного
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = S)
            )

            // Ввод цвета животного
            TextField(
                value = color,
                onValueChange = { color = it },
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
                    onClick = {
                        val animal = when (selectedAnimal) {
                            AnimalType.Cat -> Cat(name, color)
                            AnimalType.Dog -> Dog(name, color)
                            AnimalType.Frog -> Frog(name, color)
                            AnimalType.Triton -> Triton(name, color)
                            else -> null
                        }
                        onSubmit(animal)
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
