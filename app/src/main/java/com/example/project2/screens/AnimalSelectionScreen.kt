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

enum class AnimalForm {
    Mammal, Reptile
}
enum class AnimalType {
    Cat, Dog, Frog, Triton
}
@Composable
fun AnimalSelectionScreen(
    onDismissRequest: () -> Unit,
    onSubmit: (Animal?) -> Unit
) {

    var selectedType by rememberSaveable { mutableStateOf(AnimalForm.Mammal) }
    var selectedAnimal by rememberSaveable { mutableStateOf(AnimalType.Cat) }
    var name by rememberSaveable { mutableStateOf("") }
    var color by rememberSaveable { mutableStateOf("") }
    val isFormValid = name.isNotEmpty() && color.isNotEmpty()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        val animalList = remember(selectedType) {
            if (selectedType == AnimalForm.Mammal) listOf(
                AnimalType.Cat,
                AnimalType.Dog
            ) else listOf(AnimalType.Frog, AnimalType.Triton)
        }

        //сборс выбранного животного при смене типа
        LaunchedEffect(selectedType) {
            selectedAnimal = animalList.first()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(M)
                .background(Color.White)
        ) {
            Text("Animal Type", style = MaterialTheme.typography.bodyLarge)

            //для выбора типа
            Row {
                RadioButton(
                    selected = selectedType == AnimalForm.Mammal,
                    onClick = { selectedType = AnimalForm.Mammal }
                )
                Text("Mammal", modifier = Modifier.padding(start = S))

                RadioButton(
                    selected = selectedType == AnimalForm.Reptile,
                    onClick = { selectedType = AnimalForm.Reptile }
                )
                Text("Reptile", modifier = Modifier.padding(start = S))
            }

            Spacer(modifier = Modifier.padding(vertical = M))

            Text("Animal", style = MaterialTheme.typography.bodyLarge)

            // для выбора животного в зависимости от типа
            animalList.forEach { animal ->
                Row {
                    RadioButton(
                        selected = selectedAnimal == animal,
                        onClick = { selectedAnimal = animal }
                    )
                    Text(animal.name, modifier = Modifier.padding(start = S))
                }
            }

            Spacer(modifier = Modifier.padding(vertical = M))

            //для ввода имени животного
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = S)
            )

            //для ввода цвета животного
            TextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = S)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        when (selectedAnimal) {
                            AnimalType.Cat -> onSubmit(Cat(name, color))
                            AnimalType.Dog -> onSubmit(Dog(name, color))
                            AnimalType.Frog -> onSubmit(Frog(name, color))
                            AnimalType.Triton -> onSubmit(Triton(name, color))
                        }
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
